package com.example.jetpackcompose.ui.card

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.R
import com.example.jetpackcompose.ui.base.BaseActivity
import com.example.jetpackcompose.ui.theme.JetpackComposeTheme
import com.example.jetpackcompose.util.ProductItemUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingCardActivity : BaseActivity() {

    override val viewModel: ShoppingCardViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { ShoppingCardTopAppBar { finish() } },
                ) { padding ->
                    Surface(modifier = Modifier.padding(padding)) {
                        Column {
                            viewModel.items.collectAsState().value.forEach {
                                ShoppingCardView(it)
                                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                            }
                        }
                    }
                    LoadingProgress()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShoppingCardTopAppBar(callback: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.shopping_screen_title)) },
        navigationIcon = {
            IconButton(onClick = { callback() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Shopping Card Btn Back")
            }
        })
}

@Composable
private fun ShoppingCardView(cardItem: ShoppingCardViewModel.ShoppingProductItem) {
    val item = cardItem.item
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier) {
            Text(text = item.name)
            Text(text = item.price)
        }
        Spacer(modifier = Modifier.weight(1f))

        Text("Count: ${cardItem.count}")
    }
}

@Composable
@Preview(showBackground = true)
private fun ShoppingCardPreView() {
    ShoppingCardView(cardItem = ShoppingCardViewModel.ShoppingProductItem(ProductItemUtil.FakeItem, 2))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
private fun ShoppingScreenPreview() {
    val activity = LocalContext.current as Activity
    JetpackComposeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { ShoppingCardTopAppBar { activity.finish() } },
        ) { padding ->
            Surface(modifier = Modifier.padding(padding)) {
                Column {
                    ProductItemUtil.FakeListData.take(2)
                        .map { ShoppingCardViewModel.ShoppingProductItem(it, 2) }
                        .forEach {
                            ShoppingCardView(it)
                            Spacer(modifier = Modifier.padding(vertical = 8.dp))
                        }
                }
            }
        }
    }
}
