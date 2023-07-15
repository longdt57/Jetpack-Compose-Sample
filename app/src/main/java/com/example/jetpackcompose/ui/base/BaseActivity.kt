package com.example.jetpackcompose.ui.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

abstract class BaseActivity : ComponentActivity() {

    protected val toaster: Toaster by lazy { Toaster(this) }
    protected open val viewModel: BaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    @Composable
    protected fun LoadingProgress() {
        LoadingProgress(isLoading = viewModel.loading.collectAsState().value)
    }

    protected open fun initViewModel() {
        viewModel.error.launchCollectLatest(this) {
            toaster.display(it)
        }
    }
}