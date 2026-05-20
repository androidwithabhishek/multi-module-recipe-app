package abhishek.gupta.search.domain.use_cases

import abhishek.gupta.search.domain.repository.dbRepository
import javax.inject.Inject

class GetAllRecipeFormDb @Inject constructor(private val dbRepository: dbRepository)  {

    operator fun invoke() =
        dbRepository.getAllRecipes()

}