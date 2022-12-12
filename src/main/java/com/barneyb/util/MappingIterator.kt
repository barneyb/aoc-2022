package com.barneyb.util

class MappingIterator<T, R>(
    private val basis: Iterator<T>,
    private val transform: (T) -> R
) : Iterator<R> {
    override fun hasNext() =
        basis.hasNext()

    override fun next() =
        transform(basis.next())
}
