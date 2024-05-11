package com.simplespace.android.lib.standard.use_case.dispatcher.parameter

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class DispatcherParamUseCase<P, V> (
    private val dispatcher: CoroutineDispatcher,
    private inline val execute: P.() -> V
) {
    suspend operator fun invoke(param: P) {
        withContext(dispatcher) {
            execute(param)
        }
    }
}