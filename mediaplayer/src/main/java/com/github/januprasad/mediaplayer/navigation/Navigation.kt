package com.github.januprasad.mediaplayer.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.github.januprasad.common.nav.FeatureApi
import com.github.januprasad.common.nav.NavigationRoute
import com.github.januprasad.common.nav.NavigationSubGraphRoute
import com.github.januprasad.mediaplayer.screens.MediaPlayerScreen

interface MediaPlayerFeatureAPi : FeatureApi

class MediaPlayerImpl : MediaPlayerFeatureAPi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoute.MediaPlayer.route,
            startDestination = NavigationRoute.MediaPlayer.route
        ) {

            composable(route = NavigationRoute.MediaPlayer.route) {
                val mediaPlayerVideoId = it.arguments?.getString("video_id")
                mediaPlayerVideoId?.let {
                    MediaPlayerScreen(videoId = it)
                }
            }

        }
    }
}
