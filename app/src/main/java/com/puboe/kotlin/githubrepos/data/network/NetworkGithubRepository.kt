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

class NetworkGithubRepository : GithubRepository {

    companion object {
        private const val BASE_URL = "https://api.github.com/"
    }

    override fun getRepositories(username: String): Result {
        val url = URL("${BASE_URL}users/$username/repos")
        val connection = url.openConnection() as HttpURLConnection
        try {
            connection.doInput = true
            connection.connect()

            val ois = BufferedReader(InputStreamReader(connection.inputStream))
            val builder = StringBuilder()
            ois.forEachLine {
                builder.append(it)
            }
            val jsonArray = JSONArray(builder.toString())

            return Success(jsonArray.toRepositoryList())
        } catch (e: Exception) {

        } finally {
            // this is done so that there are no open connections left when this task is going to complete
            connection.disconnect()
        }
        return Failure()
    }
}