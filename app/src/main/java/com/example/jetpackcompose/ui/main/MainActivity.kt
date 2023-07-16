package com.example.jetpackcompose.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.R
import com.example.jetpackcompose.data.model.ProductItem
import com.example.jetpackcompose.ui.base.BaseActivity
import com.example.jetpackcompose.ui.card.ShoppingCardActivity
import com.example.jetpackcompose.ui.detail.ProductDetailActivity
import com.example.jetpackcompose.ui.ext.badgeLayout
import com.example.jetpackcompose.ui.theme.JetpackComposeTheme
import com.example.jetpackcompose.util.ProductItemPreviewData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 16.dp),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Column {
                        CardIconView(viewModel.cardNumber.collectAsState().value)
                        Spacer(modifier = Modifier.height(24.dp))
                        viewModel.items.collectAsState().value.forEach {
                            ProductItemView(item = it)
                            Spacer(modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
                LoadingProgress()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshData()
        viewModel.refreshCardNumber()
    }
}

@SuppressWarnings("LongMethod")
@Composable
private fun CardIconView(num: Int) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "Home Card Icon",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(40.dp)
                .clip(CircleShape)
                .clickable {
                    context.startActivity(Intent(context, ShoppingCardActivity::class.java))
                },
        )
        if (num != 0) {
            Text(
                text = num.toString(),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(2.dp)
                    .background(Color.Red, shape = CircleShape)
                    .badgeLayout(),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = Color.White,
            )
        }
    }
}

@Composable
@SuppressWarnings("LongMethod")
private fun ProductItemView(item: ProductItem) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                context.startActivity(ProductDetailActivity.getIntent(context, item))
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(text = item.name, style = MaterialTheme.typography.labelLarge)
            Text(
                text = stringResource(id = R.string.price_x, item.price.toString()),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        val statusColor = when {
            item.isAvailable() -> Color.Green
            item.isCommingSoon() -> Color.Blue
            else -> Color.Red
        }
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(statusColor),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            Icons.Default.ArrowForward,
            contentDescription = "Icon Forward ${item.name}",
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    context.startActivity(ProductDetailActivity.getIntent(context, item))
                },
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductItemViewPreview() {
    JetpackComposeTheme {
        ProductItemView(ProductItemPreviewData.FakeItem)
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    JetpackComposeTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column {
                CardIconView(2)
                Spacer(modifier = Modifier.height(24.dp))
                ProductItemPreviewData.FakeListData.forEach {
                    ProductItemView(item = it)
                    Spacer(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}
