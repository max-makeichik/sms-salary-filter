package com.salaryfilter.data.network.api_impl;

import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.salaryfilter.BuildConfig;
import com.salaryfilter.data.network.DateTimeTypeAdapter;
import com.salaryfilter.data.network.api_interface.IApiRest;
import com.salaryfilter.data.network.api_interface.INetworkClient;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Max Makeychik on 10.07.17.
 */

public class NetworkClientImpl implements INetworkClient {

    private static final String TAG = NetworkClientImpl.class.getSimpleName();
    private static final int CONNECTION_TIMEOUT_SEC = 60;
    private static final int READ_TIMEOUT_SEC = 60;

    private static final int WRITE_TIMEOUT_SEC = 60;
    private static final GsonBuilder mGsonBuilder = getGsonBuilder();
    private static OkHttpClient.Builder mClientBuilder;

    private static Retrofit.Builder mRetrofitBuilder;
    private static Retrofit mRetrofit;
    private final IApiRest mApiRest;

    public NetworkClientImpl() {
        mClientBuilder = getOkHttpBuilder();
        mRetrofitBuilder = getRetrofitBuilder();
        mRetrofit = mRetrofitBuilder.build();
        mApiRest = mRetrofit.create(IApiRest.class);
    }

    private static Retrofit.Builder getRetrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(mGsonBuilder.create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mClientBuilder.build());
    }

    private static GsonBuilder getGsonBuilder() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(DateTime.class, new DateTimeTypeAdapter())
                .setDateFormat("yyyy-MM-dd' 'HH:mm:ss")
                .setLenient();
    }

    private OkHttpClient.Builder getOkHttpBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT_SEC, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SEC, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT_SEC, TimeUnit.SECONDS);

        final List<Interceptor> interceptors = new ArrayList<>();
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(getLoggingLevel());
        interceptors.add(httpLoggingInterceptor);

        //interceptors.add(new FakeInterceptor());

        for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }
        return builder;
    }

    @NonNull
    private HttpLoggingInterceptor.Level getLoggingLevel() {
        return HttpLoggingInterceptor.Level.BODY;
    }

    public static Retrofit getRetrofit() {
        return mRetrofit;
    }

    @Override
    public IApiRest getApi() {
        return mApiRest;
    }
}
