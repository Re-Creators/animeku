package com.richardo.animeku

import com.apollographql.apollo.ApolloClient

val apolloClient = ApolloClient.builder()
    .serverUrl("https://graphql.anilist.co")
    .build()