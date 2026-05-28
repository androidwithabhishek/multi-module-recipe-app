import abhishek.gupta.common.utils.UiText
import abhishek.gupta.common.utils.navigation.NavigationRoutes
import abhishek.gupta.search.domain.model.DomainRecipeDetails
import abhishek.gupta.search.screens.recipe_list.RecipeList
import abhishek.gupta.search.screens.recipe_list.RecipeListViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest

import java.time.format.TextStyle

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeListScreen(
    recipeListViewModel: RecipeListViewModel,
    onClick: (String) -> Unit,
    onLike: () -> Unit,
    navHostController: NavHostController,
) {


    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = recipeListViewModel.navigation) {

        recipeListViewModel.navigation.flowWithLifecycle(lifecycleOwner.lifecycle).collectLatest {
            when (it) {
                is RecipeList.Navigation.GoToRecipeDetails -> {

                    navHostController.navigate(
                        NavigationRoutes.RecipeDetails.sendId(
                            id = it.id
                        )
                    )
                }

              is RecipeList.Navigation.GoFav ->
              {
                  navHostController.navigate(NavigationRoutes.FavoriteScreen.routes)
              }
            }
        }
    }

    val uiState by recipeListViewModel.uiState.collectAsState()
    val context = LocalContext.current

    val query = rememberSaveable { mutableStateOf("") }

    Scaffold(
        containerColor = Color.White, topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .statusBarsPadding()
            ) {
                TextField(
                    value = query.value, onValueChange = {
                        query.value = it
                        recipeListViewModel.onSearchQueryChanged(
                            query = query.value
                        )

                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .shadow(
                            elevation = 10.dp, shape = RoundedCornerShape(30.dp)
                        ),

                    placeholder = {
                        Text(
                            text = "Search delicious recipes 🍕",
                            fontSize = 17.sp,
                            color = Color.Gray
                        )
                    },

                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.Black
                    ),

                    singleLine = true,

                    shape = RoundedCornerShape(30.dp), colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color(0xFF2A2A2A)
                    )
                )
            }


        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onLike.invoke()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Add"
                )
            }
        }) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            when {

                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.error !is UiText.None -> {
                    Text(
                        text = uiState.error.getString(context),
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.data.isNullOrEmpty() -> {
                    Text(
                        text = "Search", modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {

                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        items(uiState.data!!) { item ->

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onClick(item.idMeal) },
                                shape = RoundedCornerShape(24.dp),
                                elevation = CardDefaults.cardElevation(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFE0DFDF) // light orange
                                )
                            ) {

                                Column {

                                    AsyncImage(
                                        model = item.strMealThumb,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(220.dp),
                                        contentScale = ContentScale.Crop
                                    )

                                    Column(
                                        modifier = Modifier.padding(14.dp)
                                    ) {

                                        Text(
                                            text = item.strMeal,
                                            style = MaterialTheme.typography.titleLarge,
                                            color = Color.Black
                                        )

                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(
                                            text = item.strInstructions,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.Black,
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 4
                                        )

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}