package abhishek.gupta.search.domain.use_cases

import abhishek.gupta.search.domain.model.DomainRecipe
import abhishek.gupta.search.domain.repository.SearchRepository
import abhishek.gupta.search.domain.repository.dbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InsertRecipeUseCase @Inject constructor(private val dbRepository: dbRepository) {

    operator fun invoke(domainRecipe: DomainRecipe) =
        flow<Unit> { dbRepository.insert(domainRecipe) }.flowOn(Dispatchers.IO)


    operator fun invoke(id: String) = flow<Unit> {
        dbRepository.delete(id)
    }.flowOn(Dispatchers.IO)

    operator fun  invoke() = flow<Unit>{
        dbRepository.getAllRecipes()
    }

}