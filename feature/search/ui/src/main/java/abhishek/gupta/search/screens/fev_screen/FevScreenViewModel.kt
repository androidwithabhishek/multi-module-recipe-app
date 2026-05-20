package abhishek.gupta.search.screens.fev_screen

import abhishek.gupta.common.utils.UiText
import abhishek.gupta.search.domain.model.DomainRecipe
import abhishek.gupta.search.domain.model.DomainRecipeDetails
import abhishek.gupta.search.domain.use_cases.GetAllRecipeFormDb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class FevScreenViewModel(private val getAllRecipeFormDb: GetAllRecipeFormDb) : ViewModel() {


    private val _fevUiState = MutableStateFlow(FevRecipe.UiState())
    val fevUiState: StateFlow<FevRecipe.UiState> = _fevUiState.asStateFlow()


    private fun getLocalFevRecipes() {
    viewModelScope.launch {
        getAllRecipeFormDb.invoke().collectLatest {

        }
    }
    }


}


object FevRecipe {
    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.None,
        val data: List<DomainRecipe>? = null,
    )
}

