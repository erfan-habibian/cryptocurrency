package com.example.cryptocurrency.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.Placeholder
import com.example.cryptocurrency.R
import com.example.cryptocurrency.generator.NumberGenerator
import com.example.cryptocurrency.model.DataX
import com.example.cryptocurrency.ui.theme.CryptocurrencyTheme
import com.example.cryptocurrency.viewModel.MainViewModel

private val imgUrl = "https://s2.coinmarketcap.com/static/img/coins/64x64/"


class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = MainViewModel()

        setContent {
            CryptocurrencyTheme {
                Surface(modifier = Modifier.fillMaxSize()){
                    designList(viewModel = viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun designList(viewModel: MainViewModel) {

    viewModel.fetchCryptoList()

    val cryptos = viewModel.list.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = colorResource(id = R.color.black),
                    titleContentColor = colorResource(id = R.color.white),
                ),
                title = {
                    Column {
                        Text(text = "Cryptocurrency",
                            fontWeight = FontWeight.Bold)
                        Text(text = "November",
                            color = colorResource(id = R.color.gray2),
                        fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    ) { innerPadding -> Column(
        modifier = Modifier
            .padding(innerPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ){
        LazyColumn{
                cryptos.value?.let { items(it.data){ item -> buildCard(item)
                Spacer(modifier = Modifier
                    .height(0.2.dp)
                    .background(color = colorResource(id = R.color.gray4)))
                }
                }
//            items(10){
//                    item -> buildCard(item.)
//                Spacer(modifier = Modifier
//                    .height(0.2.dp)
//                    .background(color = colorResource(id = R.color.gray4)))
//            }
//            cryptos.value?.let { items(it.data){ item -> buildCard(item) } }
        }
    }
    }

}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun buildCard(data: DataX) {
    val padding = 5.dp
    val context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(id = R.color.black))) {
        Row(modifier = Modifier
            .padding(5.dp)
            .border(width = 1.dp, color = Color.Black)
            .clickable {
                var intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("ID", data.id)
                intent.putExtra("PRICE", data.quote.USD.price)
                context.startActivity(intent)
            }) {
            Column(
                modifier = Modifier.weight(1F)) {
                GlideImage(
                    model = imgUrl + data.id + ".png",
                    contentDescription = "image",
                    modifier = Modifier
                        .padding(padding)
                        .size(width = 45.dp, height = 45.dp)
                )

            }
            Column (modifier = Modifier.weight(3F)){
                Text(text = data.symbol,
                    modifier = Modifier.padding(padding),
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.white))
                Text(text = data.name,
                    modifier = Modifier.padding(padding),
                    textAlign = TextAlign.End,
                    color = colorResource(id = R.color.gray2))
            }

            Column(modifier = Modifier.weight(2F),
            horizontalAlignment = Alignment.End) {
                Text(text = if (data.quote.USD.price>1000 ) NumberGenerator.numberSplitter(data.quote.USD.price.toInt()).toString()
                    else NumberGenerator.round(data.quote.USD.price).toString(),
                    modifier = Modifier.padding(padding),
                    color = colorResource(id = R.color.gray2))
                Text(text = NumberGenerator.round(data.quote.USD.percent_change_1h).toString()  + "%",
                    modifier = Modifier
                        .background(
                            colorResource(id = if (data.quote.USD.percent_change_1h >= 0) R.color.green else R.color.red),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .padding(padding),
                    color = colorResource(id = R.color.white))
            }

        }
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CryptocurrencyTheme {
        Surface(modifier = Modifier.fillMaxSize()){
            var viewModel = MainViewModel()
            designList(viewModel = viewModel)
        }
    }
}