package abhishek.gupta.search.data.repositoryImpl

import abhishek.gupta.search.data.local.Dao
import abhishek.gupta.search.domain.model.DomainRecipe
import abhishek.gupta.search.domain.repository.dbRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DBRepositoryImpl(private val dao: Dao) : dbRepository {

    override suspend fun insert(domainRecipe: DomainRecipe) {
        dao.insertByDomainRecipe(domainRecipe)
    }

    override suspend fun delete(id: String): Int {
      return  dao.deleteById(id)
    }

    override  fun getAllRecipes(): Flow<List<DomainRecipe>> {
        return dao.getAllSavedRecipes()
    }
}