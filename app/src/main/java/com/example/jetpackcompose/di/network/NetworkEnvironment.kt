package com.example.jetpackcompose.di.network

import android.content.Context
import com.example.jetpackcompose.BuildConfig
import com.example.jetpackcompose.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class NetworkEnvironment @Inject constructor(@ApplicationContext private val context: Context) {

    val baseUrl: String get() = context.getString(R.string.base_url)
    val isDebug: Boolean get() = BuildConfig.DEBUG && BuildConfig.BUILD_TYPE != "release"
}
