package abhishek.gupta.search.screens.fev_screen

import abhishek.gupta.common.utils.UiText
import abhishek.gupta.search.domain.model.DomainRecipe
import abhishek.gupta.search.domain.use_cases.DeleteRecipeUseCase
import abhishek.gupta.search.domain.use_cases.GetAllRecipeFormDb
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
    private val _fevUiState = MutableStateFlow(FavRecipeScreen.UiState())
    val fevUiState: StateFlow<FavRecipeScreen.UiState> = _fevUiState.asStateFlow()

    private val _navigation = Channel<FavRecipeScreen.Navigation>()


    val navigation: Flow<FavRecipeScreen.Navigation> = _navigation.receiveAsFlow()


    private val _uiEvent =
        MutableSharedFlow<FavRecipeScreen.UiEvent>()

    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getLocalFevRecipes()
    }


    fun onEvent(event: FavRecipeScreen.Event) {
        when (event) {
            FavRecipeScreen.Event.AlphabeticalShort -> {
                _fevUiState.update {
                    FavRecipeScreen.UiState(data = _originalList.sortedBy {
                        it.strMeal
                    })
                }
            }

            FavRecipeScreen.Event.LessIngredientShort -> {
                _fevUiState.update {
                    FavRecipeScreen.UiState(data = _originalList.sortedBy {
                        it.strMeal.length
                    })
                }
            }

            FavRecipeScreen.Event.ResetShort -> {
                _fevUiState.update {
                    FavRecipeScreen.UiState(data = _originalList)
                }
            }
            is FavRecipeScreen.Event.Delete -> {
                deleteRecipeUseCase.invoke(event.id).onEach { deletedRows ->


                    if (deletedRows > 0) {

                        _uiEvent.emit(
                            FavRecipeScreen.UiEvent.ShowToast(
                                "Recipe Deleted"
                            )
                        )

                    } else {

                        _uiEvent.emit(
                            FavRecipeScreen.UiEvent.ShowToast(
                                "Recipe Not Found"
                            )
                        )
                    }

                }.launchIn(viewModelScope)
            }

            is FavRecipeScreen.Event.GoToDetailScreen -> {

                viewModelScope.launch {
                    _navigation.send(FavRecipeScreen.Navigation.GoBackToDetailScreen(event.id))
                }

            }

            is FavRecipeScreen.Event.GoBack -> {

                viewModelScope.launch {
                    _navigation.send(FavRecipeScreen.Navigation.GoBack)
                }


            }
        }
    }

    private fun getLocalFevRecipes() {
        viewModelScope.launch {
            getAllRecipeFormDb.invoke().collectLatest { data ->
                _originalList = data.toMutableList()
                _fevUiState.update {
                    FavRecipeScreen.UiState(data = data)
                }
            }
        }
    }


}


object FavRecipeScreen {
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

