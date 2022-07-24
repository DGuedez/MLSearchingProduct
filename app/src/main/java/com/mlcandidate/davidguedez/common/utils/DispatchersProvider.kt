package com.mlcandidate.davidguedez.common.utils

import kotlinx.coroutines.Dispatchers

interface DispatchersProvider {
    fun io()= Dispatchers.IO
}