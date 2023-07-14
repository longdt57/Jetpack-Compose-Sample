package com.example.jetpackcompose.ui.base

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

abstract class BaseActivity : ComponentActivity() {

    protected open val viewModel: BaseViewModel by viewModels()

    @Composable
    protected fun LoadingProgress() {
        LoadingProgress(isLoading = viewModel.loading.collectAsState().value)
    }
}