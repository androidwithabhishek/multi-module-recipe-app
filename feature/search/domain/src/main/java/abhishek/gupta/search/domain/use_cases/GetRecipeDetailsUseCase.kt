package abhishek.gupta.search.domain.use_cases

import abhishek.gupta.common.utils.NetworkResult
import abhishek.gupta.search.domain.model.DomainRecipeDetails
import abhishek.gupta.search.domain.repository.SearchRepository
import androidx.collection.intSetOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetRecipeDetailsUseCase @Inject constructor(private val searchRepository: SearchRepository) {


    operator fun invoke(id: String) = flow<NetworkResult<DomainRecipeDetails>> {

        val response = searchRepository.getRecipeDetails(id = id)


        emit(NetworkResult.Loading())

        if (response.isSuccess) {
            emit(NetworkResult.Success(data = response.getOrThrow()))
        } else {
            emit(NetworkResult.Error(response.exceptionOrNull()?.localizedMessage))


        }
    }.catch {
        emit(NetworkResult.Error(it.localizedMessage.toString()))
    }.flowOn(Dispatchers.IO)
}