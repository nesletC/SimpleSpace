package com.simplespace.android.lib.standard.resource

sealed interface Resource {

    interface Success: Resource
    interface Failure: Resource

}