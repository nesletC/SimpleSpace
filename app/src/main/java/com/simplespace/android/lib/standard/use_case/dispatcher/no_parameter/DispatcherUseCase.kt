package com.simplespace.android.lib.standard.use_case.dispatcher.no_parameter

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class DispatcherUseCase<V> (
    private val dispatcher: CoroutineDispatcher,
    private inline val execute: () -> V
) {
    suspend operator fun invoke() : V =
        withContext(dispatcher) {
            execute()
        }
}