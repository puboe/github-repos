package com.puboe.kotlin.githubrepos.domain

abstract class Result

class Success<R>(val result: R) : Result()
class Failure : Result()