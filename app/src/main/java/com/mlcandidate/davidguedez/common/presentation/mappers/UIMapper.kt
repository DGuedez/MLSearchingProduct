package com.mlcandidate.davidguedez.common.presentation.mappers

interface UIMapper<E, V> {
    fun mapToView(input: E): V
}