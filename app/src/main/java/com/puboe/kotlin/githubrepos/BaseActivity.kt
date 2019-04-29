package com.puboe.kotlin.githubrepos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity : AppCompatActivity() {

    private val contentView: ViewGroup by lazy {
        findViewById<ViewGroup>(android.R.id.content)
    }

    private val loadingView: View by lazy {
        LayoutInflater.from(this).inflate(R.layout.loading_view, contentView, false)
    }

    fun showLoading() {
        contentView.addView(loadingView)
    }

    fun hideLoading() {
        contentView.removeView(loadingView)
    }

    fun showError() {
        val snackbar = Snackbar.make(contentView, getString(R.string.error_message), Snackbar.LENGTH_LONG)
        val snackBarView = snackbar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.error))
        snackbar.show()
    }
}