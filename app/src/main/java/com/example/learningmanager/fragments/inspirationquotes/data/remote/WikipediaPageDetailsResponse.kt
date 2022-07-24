package com.example.learningmanager.fragments.inspirationquotes.data.remote

data class WikipediaPageDetailsResponse(
    val query: WikipediaPageQuery?
)

data class WikipediaPageQuery(
    val pages: Map<String, WikipediaPage> = emptyMap<String, WikipediaPage>()
)

data class WikipediaPage(
    val thumbnail: WikipediaPageThumbnail?
)

data class WikipediaPageThumbnail(
    val source: String = ""
)