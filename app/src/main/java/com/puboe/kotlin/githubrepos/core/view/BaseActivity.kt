package com.puboe.kotlin.githubrepos.core.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.contains
import com.google.android.material.snackbar.Snackbar
import com.puboe.kotlin.githubrepos.R

abstract class BaseActivity : AppCompatActivity() {

    private val contentView: ViewGroup by lazy {
        findViewById<ViewGroup>(android.R.id.content)
    }

    private val loadingView: View by lazy {
        LayoutInflater.from(this).inflate(R.layout.loading_view, contentView, false)
    }

    fun showLoading() {
        if (!contentView.contains(loadingView)) {
            contentView.addView(loadingView)
        }
    }

    fun hideLoading() {
        contentView.removeView(loadingView)
    }

    fun showError() {
        hideLoading()
        val snackbar = Snackbar.make(contentView, getString(R.string.error_message), Snackbar.LENGTH_LONG)
        val snackBarView = snackbar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.error))
        snackbar.show()
    }
}