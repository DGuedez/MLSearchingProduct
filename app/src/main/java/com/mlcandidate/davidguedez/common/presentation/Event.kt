package com.mlcandidate.davidguedez.common.presentation

class Event<out T>(private val content:T) {
    private var handleEvent: Boolean = false

    fun getContentIfNotHandled():T?{
        return if (handleEvent){
            null
        } else {
            handleEvent = true
            content
        }
    }
}