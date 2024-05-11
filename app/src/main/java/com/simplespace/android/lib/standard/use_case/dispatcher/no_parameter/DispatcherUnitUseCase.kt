package com.simplespace.android.lib.standard.use_case.dispatcher.no_parameter

import kotlinx.coroutines.CoroutineDispatcher

abstract class DispatcherUnitUseCase (
    dispatcher: CoroutineDispatcher, execute: () -> Unit
):
    DispatcherUseCase<Unit>(dispatcher, execute)