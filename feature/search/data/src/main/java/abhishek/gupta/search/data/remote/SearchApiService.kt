package abhishek.gupta.search.data.remote

import abhishek.gupta.search.data.model.RecipeDetailsResponse
import abhishek.gupta.search.data.model.RecipeResponse
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {

//    https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata
    @GET("api/json/v1/1/search.php")
    suspend fun getRecipes(@Query("s") query: String): Response<RecipeResponse>


//    https://www.themealdb.com/api/json/v1/1/lookup.php?i=52772

    @GET("api/json/v1/1/lookup.php")
    suspend fun getRecipeDetails(@Query("i") id: String): Response<RecipeDetailsResponse>


}