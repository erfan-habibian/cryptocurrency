package com.example.cryptocurrency.model

class DataX (

    val circulating_supply: Double,
    val cmc_rank: Int,
    val date_added: String,
    val id: Int,
    val infinite_supply: Boolean,
    val last_updated: String,
    val max_supply: Double,
    val name: String,
    val num_market_pairs: Int,
    val platform: PlatformX,
    val quote: QuoteX,
    val self_reported_circulating_supply: Double,
    val self_reported_market_cap: Double,
    val slug: String,
    val symbol: String,
    val tags: List<String>,
    val total_supply: Double,
    val tvl_ratio: Double
    )