package abhishek.gupta.modularizationapp.navigation

import abhishek.gupta.common.utils.navigation.NavigationSubGraphRoutes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController


@Composable
fun RecipeNavigation(modifier: Modifier = Modifier, navigationSubGraphs: NavigationSubGraphs) {

    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = NavigationSubGraphRoutes.Search.routes,

        ) {
        navigationSubGraphs.searchFeatureApi.registerGraph(
            navGraphBuilder = this,
            navHostController = navHostController
        )
    }


}