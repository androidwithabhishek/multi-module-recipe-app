package abhishek.gupta.search.domain.repository

import abhishek.gupta.common.utils.navigation.NavigationRoutes
import abhishek.gupta.search.domain.model.DomainRecipe
import kotlinx.coroutines.flow.Flow

interface dbRepository {

    suspend fun insert(domainRecipe: DomainRecipe)
    suspend fun delete(id: String): Int


    fun getAllRecipes(): Flow<List<DomainRecipe>>

}