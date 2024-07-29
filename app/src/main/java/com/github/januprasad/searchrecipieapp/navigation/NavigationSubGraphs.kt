package com.github.januprasad.searchrecipieapp.navigation

import com.github.januprasad.features.navigation.SearchFeatureApi
import com.github.januprasad.mediaplayer.navigation.MediaPlayerFeatureAPi


data class NavigationSubGraphs(
    val searchFeatureApi: SearchFeatureApi,
    val mediaPlayerApi: MediaPlayerFeatureAPi
)
