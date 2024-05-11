package com.simplespace.android.lib.standard.resource

object Resources {

    sealed interface Result<R>{

        interface Success<R>: Result<R>, Resource.Success {
            val result: R
        }
        interface Failure<R>: Result<R>, Resource.Failure
    }

    private class ResultSuccessImplementation<R> (
        override val result: R
    ) : Result.Success<R>

    private class ResultFailureImplementation<R> : Result.Failure<R>

    fun <R>Result(isSuccess: Boolean, result: R? = null ) : Result<R> =
        if (isSuccess) ResultSuccessImplementation(result!!) else ResultFailureImplementation()


    sealed interface Error<E>{

        interface Success<E>: Error<E>, Resource.Success

        interface Failure<E>: Error<E>, Resource.Failure {
            val error: E
        }
    }

    private class ErrorSuccessImplementation<E> : Error.Success<E>

    private class ErrorFailureImplementation<E> (
        override val error: E
    ) : Error.Failure<E>

    fun <E>Error(isSuccess: Boolean, error: E? = null ) : Error<E> =
        if (isSuccess) ErrorSuccessImplementation() else ErrorFailureImplementation(error!!)



    sealed interface Odd<R, E>{

        interface Success<R, E>: Odd<R, E>, Result.Success<R>, Error.Success<E>, Resource.Success

        interface Failure<R, E>: Odd<R, E>, Result.Failure<R>, Error.Failure<E>, Resource.Failure
    }

    private class OddSuccessImplementation<R, E> (
        override val result: R
    ) : Odd.Success<R, E>

    private class OddFailureImplementation<R, E>(
        override val error: E
    ) : Odd.Failure<R, E>

    fun <R, E>Odd(isSuccess: Boolean, result: R? = null, error: E? = null) : Odd<R, E> =
        if (isSuccess) OddSuccessImplementation(result!!) else OddFailureImplementation(error!!)



    sealed interface Even<D>{

        val data: D

        interface Success<D>: Even<D>, Resource.Success

        interface Failure<D>: Even<D>, Resource.Failure
    }

    private class EvenSuccessImplementation<D>(
        override val data: D
    ) : Even.Success<D>

    private class EvenFailureImplementation<D>(
        override val data: D
    ) : Even.Failure<D>

    fun <D>Even(isSuccess: Boolean, data: D) : Even<D> =
        if (isSuccess) EvenSuccessImplementation(data) else EvenFailureImplementation(data)

    sealed interface ResultData<R, D>{

        interface Success<R, D>: ResultData<R, D>, Result.Success<R>, Even.Success<D>, Resource.Success

        interface Failure<R, D>: ResultData<R, D>, Result.Failure<R>, Even.Failure<D>, Resource.Failure
    }

    private class ResultDataSuccessImplementation<R, D>(
        override val result: R, override val data: D
    ) : ResultData.Success<R, D>

    private class ResultDataFailureImplementation<R, D>(
        override val data: D
    ) : ResultData.Failure<R, D>

    fun <R, D>ResultData(isSuccess: Boolean, data: D, result: R? = null) : ResultData<R, D> =
        if (isSuccess)
            ResultDataSuccessImplementation(result!!, data)
        else
            ResultDataFailureImplementation(data)


    sealed interface ErrorData<E, D>{

        interface Success<E, D>: ErrorData<E, D>, Error.Success<E>, Even.Success<D>, Resource.Success

        interface Failure<E, D>: ErrorData<E, D>, Error.Failure<E>, Even.Failure<D>, Resource.Failure
    }

    private class ErrorDataSuccessImplementation<E, D>(
        override val data: D
    ) : ErrorData.Success<E, D>

    private class ErrorDataFailureImplementation<E, D>(
        override val error: E, override val data: D,
    ) : ErrorData.Failure<E, D>

    fun <E, D>ErrorData(isSuccess: Boolean, data: D, error: E? = null) : ErrorData<E, D> =
        if (isSuccess)
            ErrorDataSuccessImplementation(data)
        else
            ErrorDataFailureImplementation(error!!, data)



    sealed interface Super<R, E, D>{

        interface Success<R, E, D>: Super<R, E, D>, Odd.Success<R, E>, Even.Success<D>, Resource.Success

        interface Failure<R, E, D>: Super<R, E, D>, Odd.Failure<R, E>, Even.Failure<D>, Resource.Failure
    }

    private class SuperSuccessImplementation<R, E, D>(
        override val result: R,
        override val data: D,
    ) : Super.Success<R, E, D>

    private class SuperFailureImplementation<R, E, D>(
        override val error: E,
        override val data: D,
    ) : Super.Failure<R, E, D>

    fun <R, E, D>Super(
        isSuccess: Boolean, data: D, result: R? = null, error: E? = null
    ) : Super<R, E, D> =
        if (isSuccess)
            SuperSuccessImplementation(result!!, data)
        else
            SuperFailureImplementation(error!!, data)
}