package abhishek.gupta.search.domain.use_cases

import abhishek.gupta.common.utils.NetworkResult
import abhishek.gupta.search.domain.model.DomainRecipe
import abhishek.gupta.search.domain.repository.SearchRepository
import android.R.attr.data
import android.net.Network
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GetAllRecipeUseCase @Inject constructor( private val searchRepository: SearchRepository) {

    operator fun invoke (q: String) = flow<NetworkResult<List<DomainRecipe>>> {

        emit(NetworkResult.Loading())

        val response = searchRepository.getRecipes(q)

        if (response.isSuccess){
            emit(NetworkResult.Success(data = response.getOrThrow()))
        }else{
            emit(NetworkResult.Error(message = response.exceptionOrNull()?.localizedMessage))
        }

    }.catch {
        emit(NetworkResult.Error(it.message.toString()),)
    }.flowOn(Dispatchers.IO)



}



