package com.puboe.kotlin.githubrepos.core.provider

interface DataMapper<S, T> {

    fun map(source: S): T
}