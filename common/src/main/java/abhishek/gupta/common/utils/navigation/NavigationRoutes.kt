package abhishek.gupta.common.utils.navigation



sealed class NavigationRoutes(val routes: String) {

    data object RecipeList : NavigationRoutes("/recipe_list")
    data object RecipeDetails : NavigationRoutes("/recipe_details/{id}") {
        fun sendId(id: String) = "/recipe_details/${id}"
    }

    data object FavoriteScreen : NavigationRoutes("/favorite_screen")




}

sealed class NavigationSubGraphRoutes(val routes: String){

    data object Search : NavigationSubGraphRoutes(routes ="/search" )


}