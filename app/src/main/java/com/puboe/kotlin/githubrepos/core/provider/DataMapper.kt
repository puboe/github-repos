package com.puboe.kotlin.githubrepos.core.provider

interface DataMapper<in S, out T> {

    fun map(source: S): T
}