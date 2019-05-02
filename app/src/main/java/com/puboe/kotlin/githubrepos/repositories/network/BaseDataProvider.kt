package com.puboe.kotlin.githubrepos.repositories.network

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

abstract class BaseDataProvider {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    protected fun doGet(requestUrl: String): String {
        val url = URL(requestUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()

        val ois = BufferedReader(InputStreamReader(connection.inputStream))
        val builder = StringBuilder()
        ois.forEachLine {
            builder.append(it)
        }

        connection.disconnect()

        return builder.toString()
    }
}