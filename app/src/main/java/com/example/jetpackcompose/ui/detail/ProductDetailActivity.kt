package com.example.jetpackcompose.ui.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.jetpackcompose.R
import com.example.jetpackcompose.data.model.ProductItem
import com.example.jetpackcompose.ui.base.BaseActivity
import com.example.jetpackcompose.ui.base.launchCollectLatest
import com.example.jetpackcompose.ui.theme.JetpackComposeTheme
import com.example.jetpackcompose.util.ProductItemPreviewData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailActivity : BaseActivity() {

    private val item: ProductItem get() = intent.getParcelableExtra(KEY_DATA)!!
    override val viewModel: ProductDetailViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressWarnings("MagicNumber")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { ProductDetailTopAppBar() },
                    floatingActionButton = { ProductDetailFloatingActionButton { viewModel.addToCard(item) } },
                    floatingActionButtonPosition = FabPosition.Center
                ) {
                    Surface(modifier = Modifier.padding(it)) {
                        ProductDetailView(item = item)
                    }
                }
            }
        }

        viewModel.addSuccess.launchCollectLatest(this) {
            if (it) {
                lifecycleScope.launch {
                    toaster.display("${item.name} is added to card")
                    delay(1000)
                    finish()
                }
            }
        }
    }

    companion object {
        private const val KEY_DATA = "data"
        fun getIntent(context: Context, item: ProductItem): Intent {
            return Intent(context, ProductDetailActivity::class.java).putExtra(KEY_DATA, item)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductDetailTopAppBar() {
    val activity = LocalContext.current as Activity
    TopAppBar(
        title = { Text(text = stringResource(R.string.detail_screen_title)) },
        navigationIcon = {
            IconButton(onClick = { activity.finish() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Detail Btn Back")
            }
        })
}

@Composable
private fun ProductDetailFloatingActionButton(callback: () -> Unit) {
    ExtendedFloatingActionButton(
        text = { Text(text = stringResource(R.string.detail_screen_btn_add)) },
        icon = {},
        onClick = { callback() })
}

@Composable
private fun ProductDetailView(item: ProductItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = item.name, fontWeight = FontWeight.Medium)
        Text(text = item.content.orEmpty(), modifier = Modifier.padding(start = 16.dp, top = 16.dp))
        Text(text = item.price, modifier = Modifier.padding(top = 16.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductDetailPreView() {
    ProductDetailView(ProductItemPreviewData.FakeItem)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun DetailScreenPreview() {
    JetpackComposeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { ProductDetailTopAppBar() },
            floatingActionButton = { ProductDetailFloatingActionButton { } },
            floatingActionButtonPosition = FabPosition.Center
        ) {
            Surface(modifier = Modifier.padding(it)) {
                ProductDetailView(item = ProductItemPreviewData.FakeItem)
            }
        }
    }
}
