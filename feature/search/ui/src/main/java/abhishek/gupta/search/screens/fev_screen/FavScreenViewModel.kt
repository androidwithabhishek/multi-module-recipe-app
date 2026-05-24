package abhishek.gupta.search.screens.fev_screen

import abhishek.gupta.common.utils.UiText
import abhishek.gupta.search.domain.model.DomainRecipe
import abhishek.gupta.search.domain.use_cases.DeleteRecipeUseCase
import abhishek.gupta.search.domain.use_cases.GetAllRecipeFormDb
import abhishek.gupta.search.screens.recipe_list.RecipeList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavScreenViewModel @Inject constructor (
    private val getAllRecipeFormDb: GetAllRecipeFormDb,
   private val deleteRecipeUseCase: DeleteRecipeUseCase,
) : ViewModel() {

    private var _originalList = mutableListOf<DomainRecipe>()
    val originalList = _originalList
    private val _fevUiState = MutableStateFlow(FevRecipeScreen.UiState())
    val fevUiState: StateFlow<FevRecipeScreen.UiState> = _fevUiState.asStateFlow()

    private val _navigation = Channel<FevRecipeScreen.Navigation>()


    val navigation: Flow<FevRecipeScreen.Navigation> = _navigation.receiveAsFlow()


    private val _uiEvent =
        MutableSharedFlow<FevRecipeScreen.UiEvent>()

    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getLocalFevRecipes()
    }


    fun onEvent(event: FevRecipeScreen.Event) {
        when (event) {
            FevRecipeScreen.Event.AlphabeticalShort -> {
                _fevUiState.update {
                    FevRecipeScreen.UiState(data = _originalList.sortedBy {
                        it.strMeal
                    })
                }
            }

            FevRecipeScreen.Event.LessIngredientShort -> {
                _fevUiState.update {
                    FevRecipeScreen.UiState(data = _originalList.sortedBy {
                        it.strMeal.length
                    })
                }
            }

            FevRecipeScreen.Event.ResetShort -> {
                _fevUiState.update {
                    FevRecipeScreen.UiState(data = _originalList)
                }
            }
            is FevRecipeScreen.Event.Delete -> {
                deleteRecipeUseCase.invoke(event.id).onEach { deletedRows ->


                    if (deletedRows > 0) {

                        _uiEvent.emit(
                            FevRecipeScreen.UiEvent.ShowToast(
                                "Recipe Deleted"
                            )
                        )

                    } else {

                        _uiEvent.emit(
                            FevRecipeScreen.UiEvent.ShowToast(
                                "Recipe Not Found"
                            )
                        )
                    }

                }.launchIn(viewModelScope)
            }

            is FevRecipeScreen.Event.GoToDetailScreen -> {

                viewModelScope.launch {
                    _navigation.send(FevRecipeScreen.Navigation.GoBackToDetailScreen(event.id))
                }

            }

            is FevRecipeScreen.Event.GoBack -> {

                viewModelScope.launch {
                    _navigation.send(FevRecipeScreen.Navigation.GoBack)
                }


            }
        }
    }

    private fun getLocalFevRecipes() {
        viewModelScope.launch {
            getAllRecipeFormDb.invoke().collectLatest { data ->
                _originalList = data.toMutableList()
                _fevUiState.update {
                    FevRecipeScreen.UiState(data = data)
                }
            }
        }
    }


}


object FevRecipeScreen {
    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.None,
        val data: List<DomainRecipe>? = null,
    )

    sealed interface Navigation {
        data class GoBackToDetailScreen(val id: String) : Navigation

        data object GoBack : Navigation
    }

    sealed interface Event {
        data object AlphabeticalShort : Event
        data object LessIngredientShort : Event
        data object ResetShort : Event

        data class GoToDetailScreen(val id: String) : Event

        data class Delete(val id: String) : Event

        data object GoBack : Event
    }

    sealed interface UiEvent {
        data class ShowToast(val message: String) : UiEvent
    }
}

