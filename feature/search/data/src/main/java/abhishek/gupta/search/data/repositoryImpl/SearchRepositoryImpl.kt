package abhishek.gupta.search.data.repositoryImpl

import abhishek.gupta.search.data.remote.SearchApiService
import abhishek.gupta.search.data.mapper.toDomainRecipe
import abhishek.gupta.search.data.mapper.toDomainRecipeDetails
import abhishek.gupta.search.domain.model.DomainRecipe
import abhishek.gupta.search.domain.model.DomainRecipeDetails
import abhishek.gupta.search.domain.repository.SearchRepository

class SearchRepositoryImpl(private val searchApiService: SearchApiService) : SearchRepository {

    override suspend fun getRecipes(s: String): Result<List<DomainRecipe>> {

        return try {
            val response = searchApiService.getRecipes(s)

            if (response.isSuccessful) {


                response.body()?.meals?.let {
                    Result.success(it.toDomainRecipe())
                } ?: run {
                    Result.failure(Exception("error"))
                }

            } else {
                Result.failure(Exception("error"))
            }
        } catch (
            e: Exception,
        ) {
            Result.failure(e)
        }


    }


    override suspend fun getRecipeDetails(id: String): Result<DomainRecipeDetails> {
        return try {
            val response = searchApiService.getRecipeDetails(id)

            if (response.isSuccessful) {
                response.body()?.meals?.let {
                    if (it.isNotEmpty()) {
                        Result.success(it.first().toDomainRecipeDetails())

                    } else {
                        Result.failure(Exception("No Data Found"))
                    }

                } ?: run {
                    Result.failure(Exception("No Data Found"))

                }
            } else {
                Result.failure(Exception("Something went wrong"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

}