package com.salaryfilter.data.network.api_impl;

import com.salaryfilter.data.network.api_interface.ApiService;
import com.salaryfilter.data.network.api_interface.INetworkClient;

import timber.log.Timber;

/**
 * Created by Max Makeychik on 26.06.17.
 */

public class ApiServiceImpl implements ApiService {

    private final INetworkClient networkClient;

    public ApiServiceImpl(INetworkClient networkClient) {
        this.networkClient = networkClient;
        Timber.d("ApiServiceImpl");
    }
}