package com.simplespace.android.lib.standard.use_case.suspend.no_parameter

interface UseCase<V> {
    suspend operator fun invoke() : V
}