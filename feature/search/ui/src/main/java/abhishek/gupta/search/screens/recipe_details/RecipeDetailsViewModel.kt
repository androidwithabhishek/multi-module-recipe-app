package abhishek.gupta.search.screens.recipe_details

import abhishek.gupta.common.utils.NetworkResult
import abhishek.gupta.common.utils.UiText
import abhishek.gupta.search.domain.model.DomainRecipe
import abhishek.gupta.search.domain.model.DomainRecipeDetails
import abhishek.gupta.search.domain.use_cases.GetRecipeDetailsUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase) :
    ViewModel() {


    private val _uiStateForDetails = MutableStateFlow(RecipeDetails.RecipeDetailsUiState())
    val uiStateForDetails: StateFlow<RecipeDetails.RecipeDetailsUiState> get() = _uiStateForDetails.asStateFlow()


    val _navigation = Channel<RecipeDetails.Navigation>()
    val navigation: Flow<RecipeDetails.Navigation> = _navigation.receiveAsFlow()


    fun onEvent(event: RecipeDetails.Event) {
        _uiStateForDetails.update {
            it.copy(isLoading = true)
        }
        when (event) {
            is RecipeDetails.Event.FetchRecipeDetails -> {
                searchRecipeDetails(event.id)
            }

            is RecipeDetails.Event.GoBackEvent -> {
                viewModelScope.launch {
                    _navigation.send(RecipeDetails.Navigation.GoBack)
                }
            }

            is RecipeDetails.Event.InserFevRedipe -> {


            }

            is RecipeDetails.Event.DeleteFevRedipe -> {

            }

        }


    }

    fun DomainRecipeDetails.toDomainRecipe(): DomainRecipe {
        return DomainRecipe(
            idMeal,
            strArea,
            strMeal,
            strMealThumb,
            strCategory,
            strTags,
            strYoutube,
            strInstructions
        )
    }

    private fun searchRecipeDetails(id: String) {

        getRecipeDetailsUseCase.invoke(id).onEach { result ->
            when (result) {

                is NetworkResult.Loading -> {

                    _uiStateForDetails.update { currentState ->
                        currentState.copy(
                            isLoading = true,
                        )
                    }

                }

                is NetworkResult.Error -> {
                    _uiStateForDetails.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            error = UiText.RemoteString(result.message.toString())

                        )
                    }
                }

                is NetworkResult.Success -> {
                    _uiStateForDetails.update { currentState ->
                        currentState.copy(data = result.data, isLoading = false)
                    }
                }
            }

        }.launchIn(viewModelScope)


    }


}

object RecipeDetails {

    data class RecipeDetailsUiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.None,
        val data: DomainRecipeDetails? = null,
    )

    sealed interface Navigation {
        data object GoBack : Navigation
    }

    sealed interface Event {
        data class FetchRecipeDetails(val id: String) : Event
        data object GoBackEvent : Event

        data class InserFevRedipe(val domainRecipe: DomainRecipe) : Event

        data class DeleteFevRedipe(val id: String) : Event


    }

}