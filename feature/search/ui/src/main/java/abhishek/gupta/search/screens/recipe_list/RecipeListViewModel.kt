package abhishek.gupta.search.screens.recipe_list

import abhishek.gupta.common.utils.NetworkResult
import abhishek.gupta.common.utils.UiText
import abhishek.gupta.search.domain.model.DomainRecipe
import abhishek.gupta.search.domain.use_cases.GetAllRecipeUseCase
import abhishek.gupta.search.screens.recipe_details.RecipeDetails
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(private val getAllRecipeUseCase: GetAllRecipeUseCase) :
    ViewModel() {

    private val searchQuery = MutableStateFlow("")
    private val _uiState = MutableStateFlow(RecipeList.UiState())

    val uiState: StateFlow<RecipeList.UiState> get() = _uiState.asStateFlow()


    private val _navigation = Channel<RecipeList.Navigation>()
    val navigation: Flow<RecipeList.Navigation> = _navigation.receiveAsFlow()




    init {
        observeSearchQuery()
    }


    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .collectLatest { query ->

                    if (query.length >= 3) {
                        onEvent(
                            RecipeList.Event.FetchRecipeList(query)
                        )
                    } else if (query.isBlank()) {
                        _uiState.update {
                            it.copy(data = emptyList())
                        }

                    }
                }
        }
    }

    fun onEvent(event: RecipeList.Event) {
        when (event) {
            is RecipeList.Event.FetchRecipeList -> {
                search(event.name)
            }

            is RecipeList.Event.GoToRecipeDetails -> {
                viewModelScope.launch {
                    _navigation.send(
                        RecipeList.Navigation.GoToRecipeDetails(event.id)


                    )
                }

            }
            is RecipeList.Event.GoToFav ->
            {
                viewModelScope.launch {
                    _navigation.send(RecipeList.Navigation.GoFav
                    )
                }
            }
        }
    }


    fun search(q: String) {

        getAllRecipeUseCase.invoke(q).onEach { result ->
            when (result) {


                is NetworkResult.Error -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            error = UiText.RemoteString(result.message.toString()),
                            isLoading = false,
                        )
                    }

                }


                is NetworkResult.Loading -> {
                    _uiState.update { currentState ->

                        currentState.copy(
                            isLoading = true,
                            error = UiText.None // optional reset
                        )

                    }
                }


                is NetworkResult.Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(data = result.data, isLoading = false)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }


}


object RecipeList {
    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.None,
        val data: List<DomainRecipe>? = null,
    )

    sealed interface Navigation {
        data class GoToRecipeDetails(val id: String) : Navigation
        data object GoFav: Navigation
    }

    sealed interface Event {
        data class FetchRecipeList(val name: String) : Event

        data class GoToRecipeDetails(val id: String) : Event

        data object GoToFav: Event



    }


}