package com.prashant.sampleapplication.domain.mapper

interface IMapperService<T : Any, O : Any> {
    fun map(input: T): O
}