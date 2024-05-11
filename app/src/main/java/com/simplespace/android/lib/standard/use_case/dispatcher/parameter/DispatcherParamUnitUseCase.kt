package com.simplespace.android.lib.standard.use_case.dispatcher.parameter

import kotlinx.coroutines.CoroutineDispatcher

abstract class DispatcherParamUnitUseCase<P> (
    dispatcher: CoroutineDispatcher, execute: P.() -> Unit
) :
    DispatcherParamUseCase<P, Unit>(dispatcher, execute)