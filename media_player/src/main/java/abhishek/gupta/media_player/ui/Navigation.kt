package abhishek.gupta.media_player.ui

import abhishek.gupta.common.utils.navigation.FeatureApi
import abhishek.gupta.common.utils.navigation.NavigationRoutes
import abhishek.gupta.common.utils.navigation.NavigationSubGraphRoutes
import androidx.navigation.compose.composable
import androidx.navigation.navigation


interface MediaPlayerFeatureApi : FeatureApi


class MediaPlayerFeatureApiImpl : MediaPlayerFeatureApi {
    override fun registerGraph(
        navGraphBuilder: androidx.navigation.NavGraphBuilder,
        navHostController: androidx.navigation.NavHostController,


        ) {

        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.MediaPlayer.routes,
            startDestination = NavigationRoutes.MediaPlayerScreen.routes
        ) {
            composable(NavigationRoutes.MediaPlayerScreen.routes) {

            }
        }

    }


}