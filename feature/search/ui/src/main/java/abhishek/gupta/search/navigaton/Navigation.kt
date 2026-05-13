package abhishek.gupta.search.navigaton

import RecipeListScreen
import abhishek.gupta.common.utils.navigation.FeatureApi
import abhishek.gupta.common.utils.navigation.NavigationRoutes
import abhishek.gupta.common.utils.navigation.NavigationSubGraphRoutes
import abhishek.gupta.search.screens.recipe_details.RecipeDetailScreen
import abhishek.gupta.search.screens.recipe_details.RecipeDetails
import abhishek.gupta.search.screens.recipe_details.RecipeDetailsViewModel
import abhishek.gupta.search.screens.recipe_list.RecipeList

import abhishek.gupta.search.screens.recipe_list.RecipeListViewModel
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation


interface SearchFeatureApi : FeatureApi {

}


class SearchFeatureApiImpl : SearchFeatureApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController,
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.Search.routes,
            startDestination = NavigationRoutes.RecipeList.routes
        ) {
            composable(NavigationRoutes.RecipeList.routes) { backStackEntry ->

                val viewModel = hiltViewModel<RecipeListViewModel>()

                RecipeListScreen(
                    recipeListViewModel = viewModel,
                    onClick = {
                        viewModel.onEvent(RecipeList.Event.GoToRecipeDetails(it))
                              },
                    navHostController = navHostController,
                )

            }

            composable(NavigationRoutes.RecipeDetails.routes) {

                val viewModel = hiltViewModel<RecipeDetailsViewModel>()
                val id = it.arguments?.getString("id")
                id?.let {
                    viewModel.onEvent(
                        RecipeDetails.Event.FetchRecipeDetails(id = it)
                    )
                }

                RecipeDetailScreen(
                    recipeDetailsViewModel = viewModel,
                    navHostController = navHostController,
                    onNavigationClick = {
                        viewModel.onEvent(RecipeDetails.Event.GoBackEvent)
                    },
                )

            }
        }
    }


}

