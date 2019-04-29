package com.puboe.kotlin.githubrepos.data.network

import com.puboe.kotlin.githubrepos.data.GithubRepository
import com.puboe.kotlin.githubrepos.domain.Failure
import com.puboe.kotlin.githubrepos.domain.Result
import com.puboe.kotlin.githubrepos.domain.Success
import com.puboe.kotlin.githubrepos.toRepositoryList
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Network [GithubRepository] implementation.
 */
class NetworkGithubRepository : GithubRepository {

    companion object {
        private const val BASE_URL = "https://api.github.com/"
    }

    override fun getRepositories(username: String): Result {
        try {
            val response = doGet("${BASE_URL}users/$username/repos")
            return Success(JSONArray(response).toRepositoryList())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Failure()
    }

    override fun getCommits(username: String, repository: String): Result {
        try {
            val response = doGet("${BASE_URL}users/$username/$repository/commits")
            return Success(JSONArray(response).toRepositoryList()) // TODO
        } catch (e: Exception) {
        }
        return Failure()
    }

    private fun doGet(url: String): String {
        val url = URL(url)
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