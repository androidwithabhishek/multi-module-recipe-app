package abhishek.gupta.search.navigaton

import RecipeListScreen
import abhishek.gupta.common.utils.navigation.FeatureApi
import abhishek.gupta.common.utils.navigation.NavigationRoutes
import abhishek.gupta.common.utils.navigation.NavigationSubGraphRoutes
import abhishek.gupta.search.screens.fev_screen.FavScreen
import abhishek.gupta.search.screens.fev_screen.FavScreenViewModel
import abhishek.gupta.search.screens.fev_screen.FevRecipeScreen
import abhishek.gupta.search.screens.recipe_details.RecipeDetailScreen
import abhishek.gupta.search.screens.recipe_details.RecipeDetails
import abhishek.gupta.search.screens.recipe_details.RecipeDetailsViewModel
import abhishek.gupta.search.screens.recipe_list.RecipeList

import abhishek.gupta.search.screens.recipe_list.RecipeListViewModel
import android.util.Log
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import hilt_aggregated_deps._abhishek_gupta_search_di_UiModule


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

            Log.d("abhi", "_abhishek_gupta_search_di_UiModule")

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
                    onDelete = {
                        viewModel.onEvent(
                            RecipeDetails.Event.DeleteFevRecipe(it)
                        )
                    },
                    onLike = {

                        viewModel.onEvent(
                            RecipeDetails.Event.InsertFevRecipe(it)
                        )
                        viewModel.onEvent(RecipeDetails.Event.GoToFav)
                    }
                )

            }

            composable(NavigationRoutes.FavoriteScreen.routes) {
                val viewModel = hiltViewModel<RecipeDetailsViewModel>()
                val id = it.arguments?.getString("id")
                val favoriteViewmodel = hiltViewModel<FavScreenViewModel>()
                FavScreen(
                    modifier = Modifier,
                    fevScreenViewModel = favoriteViewmodel,
                    onClick = {
                        favoriteViewmodel.onEvent(FevRecipeScreen.Event.GoToDetailScreen(id = it))
//                        id?.let {
//                            viewModel.onEvent(
//                                RecipeDetails.Event.FetchRecipeDetails(id = it)
//                            )
//                        }

                    },
                    navHostController = navHostController,
                    onNavClick = {
                        favoriteViewmodel.onEvent(FevRecipeScreen.Event.GoBack)

                    }
                )

            }
        }
    }


}

