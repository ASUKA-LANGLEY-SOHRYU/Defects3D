package com.michael.a3dprintingdefects.app.core.mapper

interface IMapper<T, K> {
    fun map(input: T): K
}

interface IBidirectionalMapper<T, K>: IMapper<T, K>{
    fun reverseMap(input: K): T
}
