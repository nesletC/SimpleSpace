package com.simplespace.android.lib.standard.use_case.suspend.parameter

interface ParamUseCase<P, V> {
    suspend operator fun invoke(param: P) : V
}