package abhishek.gupta.search.domain.repository

import abhishek.gupta.search.domain.model.DomainRecipe
import abhishek.gupta.search.domain.model.DomainRecipeDetails

interface SearchRepository {

   suspend fun getRecipes(s: String): Result<List<DomainRecipe>>
   suspend fun getRecipeDetails(id: String): Result<DomainRecipeDetails>
   


}

