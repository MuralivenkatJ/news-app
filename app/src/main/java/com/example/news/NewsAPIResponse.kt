package com.example.news

data class NewsAPIResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
