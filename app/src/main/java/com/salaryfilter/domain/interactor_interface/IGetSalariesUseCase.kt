package com.salaryfilter.domain.interactor_interface

import com.salaryfilter.domain.global.model.Salary
import io.reactivex.Flowable

/**
 * Created by Max Makeichik on 08-May-18.
 */
interface IGetSalariesUseCase: IUseCase {
    fun getSalaryListOneByOne(): Flowable<Salary>
}