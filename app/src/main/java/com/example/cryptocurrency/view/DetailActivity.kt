package com.example.cryptocurrency.view

import CryptoDetail
import DataXXX
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.cryptocurrency.R
import com.example.cryptocurrency.generator.NumberGenerator
import com.example.cryptocurrency.ui.theme.CryptocurrencyTheme
import com.example.cryptocurrency.viewModel.DetailViewModel

private val imgUrl = "https://s2.coinmarketcap.com/static/img/coins/64x64/"
private var price = 10.0
class DetailActivity : ComponentActivity() {

    private lateinit var viewModel : DetailViewModel
    private var id = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        id = intent.getIntExtra("ID", 1)
        price = intent.getDoubleExtra("PRICE", 2023000.0)


        viewModel = DetailViewModel()
        viewModel.fetchDetail(id)

        setContent {
            CryptocurrencyTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    buildScreen(detailLiveData = viewModel.detail)
//                    launchScreen()
                }
            }
        }
    }

}

@Composable
fun buildScreen(detailLiveData: LiveData<CryptoDetail>){
    val crypto = detailLiveData.observeAsState()
    crypto.value?.let{
        launchScreen(data = it.data)
    }
}



@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun launchScreen(data: DataXXX){
//fun launchScreen() {

    val context = LocalContext.current
    val state = rememberScrollState()
    val smallFont = 12.sp
    val smallPadding = Modifier.padding(top = 2.dp, bottom = 2.dp,
        start = 10.dp, end = 10.dp)
    val bigPadding = Modifier.padding(top = 5.dp, bottom = 5.dp,
        start = 10.dp, end = 10.dp)
    Column(modifier = Modifier
        .background(colorResource(id = R.color.black))
        .verticalScroll(state)) {
        Image(painter = painterResource(id = R.drawable.pic2),
            contentDescription = "hi",
        modifier = Modifier.fillMaxWidth())
        Row(modifier = Modifier.fillMaxWidth()) {
            GlideImage(
                model = imgUrl + "1.png",
                contentDescription = "image",
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
                    .size(width = 45.dp, height = 45.dp)
                    .weight(1F)
            )
            Column(modifier = Modifier.weight(2F)) {
                Text(text = data.a.symbol,
                    modifier = Modifier.padding(top = 10.dp, bottom = 5.dp, start = 10.dp, end = 10.dp),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.white),
                    fontSize = 18.sp
                )
                Text(text = data.a.name,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp,
                        start = 10.dp, end = 10.dp),
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.gray2),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            Column(horizontalAlignment = Alignment.End,
                modifier = Modifier.weight(3F)) {
                Text(text = NumberGenerator.roundToSixDigits(price).toString() + " $",
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.blue),
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                    )

                Row(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)) {
                    Text(text = "is active: ",
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.white)
                    )
                    Image(painter = painterResource(id = if(data.a.is_active == 1) R.drawable.icon_check else R.drawable.icon_cancel),
                        contentDescription = "hi",
                        modifier = Modifier
                            .size(width = 20.dp, height = 20.dp)
                            .fillMaxHeight())
                }

            }
        }
        Text(text = getDesc(),
            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp),
            color = colorResource(id = R.color.white),
            fontSize = 12.sp
        )

        Text(text = "Percentages:",
            fontWeight = FontWeight.ExtraBold,
        color = colorResource(id = R.color.gray2),
        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp),
        fontSize = 22.sp)
        Row(modifier = Modifier.fillMaxWidth()){
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = smallPadding) {
                    Text(text = "(01h): ",
                        color = colorResource(id = R.color.white),
                        fontSize = smallFont
                    )
                    Text(text = NumberGenerator.roundToFourDigits(data.a.quote.USD.percent_change_1h).toString() + " %",
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.white),
                        modifier = Modifier
                            .background(
                                colorResource(
                                    id = if (data.a.quote.USD.percent_change_1h > 0) R.color.green else
                                        R.color.red
                                ),
                                shape = RoundedCornerShape(5.dp)
                            )
                            .padding(1.dp),
                        fontSize = smallFont
                    )
                }
                Row (modifier = smallPadding){
                    Text(text = "(24h): ",
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.white),
                        fontSize = smallFont
                    )
                    Text(text = NumberGenerator.roundToFourDigits(data.a.quote.USD.percent_change_24h).toString() + " %",
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.white),
                        modifier = Modifier
                            .background(
                                colorResource(
                                    id = if (data.a.quote.USD.percent_change_24h > 0) R.color.green else
                                        R.color.red
                                ),
                                shape = RoundedCornerShape(5.dp)
                            )
                            .padding(1.dp),
                        fontSize = smallFont
                    )
                }
                Row (modifier = smallPadding){
                    Text(text = "(07d): ",
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.white),
                        fontSize = smallFont
                    )
                    Text(text = NumberGenerator.roundToFourDigits(data.a.quote.USD.percent_change_7d).toString() + " %",
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.white),
                        modifier = Modifier
                            .background(
                                colorResource(
                                    id = if (data.a.quote.USD.percent_change_7d > 0) R.color.green else
                                        R.color.red
                                ),
                                shape = RoundedCornerShape(5.dp)
                            )
                            .padding(1.dp),
                        fontSize = smallFont
                    )
                }
            }
            Column(modifier = Modifier.weight(1F)){
                Row(modifier = smallPadding){
                    Text(text = "(30d): ",
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.white),
                        fontSize = smallFont
                    )
                    Text(text = NumberGenerator.roundToFourDigits(data.a.quote.USD.percent_change_30d).toString() + " %",
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.white),
                        modifier = Modifier
                            .background(
                                colorResource(
                                    id = if (data.a.quote.USD.percent_change_30d > 0) R.color.green else
                                        R.color.red
                                ),
                                shape = RoundedCornerShape(5.dp)
                            )
                            .padding(1.dp),
                        fontSize = smallFont
                    )
                }
                Row(modifier = smallPadding){
                    Text(text = "(60d): ",
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.white),
                        fontSize = smallFont
                    )
                    Text(text = NumberGenerator.roundToFourDigits(data.a.quote.USD.percent_change_60d).toString() + " %",
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.white),
                        modifier = Modifier
                            .background(
                                colorResource(
                                    id = if (data.a.quote.USD.percent_change_60d > 0) R.color.green else
                                        R.color.red
                                ),
                                shape = RoundedCornerShape(5.dp)
                            )
                            .padding(1.dp),
                        fontSize = smallFont
                    )
                }
                Row(modifier = smallPadding){
                    Text(text = "(90d): ",
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.white),
                        fontSize = smallFont
                    )
                    Text(text = NumberGenerator.roundToFourDigits(data.a.quote.USD.percent_change_90d).toString() + " %",
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.white),
                        modifier = Modifier
                            .background(
                                colorResource(
                                    id = if (data.a.quote.USD.percent_change_90d > 0) R.color.green else
                                        R.color.red
                                ),
                                shape = RoundedCornerShape(5.dp)
                            )
                            .padding(1.dp),
                        fontSize = smallFont
                    )
                }
            }
        }

        Text(text = "About:",
            fontWeight = FontWeight.ExtraBold,
            color = colorResource(id = R.color.gray2),
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp),
            fontSize = 22.sp)
        Column {

            Text(text = "https://bitcoin.org/",
                color = colorResource(id = R.color.blue),
                modifier = Modifier
                    .padding(
                        top = 5.dp, bottom = 5.dp,
                        start = 10.dp, end = 10.dp
                    )
                    .clickable {
                        var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://bitcoin.org/"))
                        context.startActivity(intent)
                    })
            Text(text = "https://www.oklink.com/btc",
                color = colorResource(id = R.color.blue),
                modifier = Modifier
                    .padding(
                        top = 5.dp, bottom = 5.dp,
                        start = 10.dp, end = 10.dp
                    )
                    .clickable {
                        var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.oklink.com/btc"))
                        context.startActivity(intent)
                    })

            Text(text = "https://reddit.com/r/bitcoin",
                color = colorResource(id = R.color.blue),
                modifier = Modifier
                    .padding(
                        top = 5.dp, bottom = 5.dp,
                        start = 10.dp, end = 10.dp
                    )
                    .clickable {
                        var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://reddit.com/r/bitcoin"))
                        context.startActivity(intent)
                    })
            Text(text = "https://github.com/bitcoin/bitcoin",
                color = colorResource(id = R.color.blue),
                modifier = Modifier
                    .padding(
                        top = 5.dp, bottom = 5.dp,
                        start = 10.dp, end = 10.dp
                    )
                    .clickable {
                        var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/bitcoin/bitcoin"))
                        context.startActivity(intent)
                    })
        }


    }


}

@Preview(showBackground = true)
@Composable
fun showText() {
//    launchScreen()
}


fun getDesc(): String{
    return "Bitcoin (BTC) is a cryptocurrency launched in 2010." +
            " Users are able to generate BTC through the process of " +
            "mining. Bitcoin has a current supply of 19,537,218. The" +
            " last known price of Bitcoin is 36,555.62175355 USD and is " +
            "up 3.74 over the last 24 hours. It is currently trading " +
            "on 10540 active market(s) with $23,743,448,404.80 traded " +
            "over the last 24 hours. More information can be found at " +
            "https://bitcoin.org/."
}
