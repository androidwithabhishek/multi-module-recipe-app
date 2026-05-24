package abhishek.gupta.search.screens.fev_screen

import abhishek.gupta.common.utils.UiText
import abhishek.gupta.common.utils.navigation.NavigationRoutes
import android.R.attr.onClick
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavScreen(
    modifier: Modifier = Modifier,
    fevScreenViewModel: FavScreenViewModel,
    onClick: (id: String) -> Unit,
    navHostController: NavHostController,
    onNavClick: () -> Unit
) {

    var showDropdown by rememberSaveable {
        mutableStateOf(false)
    }

    var selectedIndex by rememberSaveable {
        mutableIntStateOf(-1)
    }

    val uiState by fevScreenViewModel.fevUiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    /*
     * Navigation Events
     */
    LaunchedEffect(Unit) {

        fevScreenViewModel.navigation
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { navigation ->

                when (navigation) {

                    is FevRecipeScreen.Navigation.GoBackToDetailScreen -> {

                        navHostController.navigate(
                            NavigationRoutes.RecipeDetails.sendId(
                                id = navigation.id
                            )
                        )
                    }

                    is FevRecipeScreen.Navigation.GoBack -> {
                        navHostController.popBackStack()
                    }
                }
            }
    }


    LaunchedEffect(Unit) {

        fevScreenViewModel.uiEvent.collectLatest { event ->

            when (event) {

                is FevRecipeScreen.UiEvent.ShowToast -> {

                    Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {

            TopAppBar(

                title = {
                    Text(
                        text = "Favorites",
                        modifier = Modifier.padding(start = 8.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.5.sp
                        )
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick = onNavClick,
                        modifier = Modifier.padding(start = 12.dp)
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },

                actions = {

                    IconButton(
                        onClick = {
                            showDropdown = !showDropdown
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More Options",
                            tint = Color.White
                        )
                    }

                    DropdownMenu(
                        expanded = showDropdown,
                        onDismissRequest = {
                            showDropdown = false
                        }
                    ) {

                        DropdownMenuItem(
                            text = {
                                Text("Alphabetical")
                            },

                            onClick = {

                                selectedIndex = 0
                                showDropdown = false

                                fevScreenViewModel.onEvent(
                                    FevRecipeScreen.Event.AlphabeticalShort
                                )
                            },

                            leadingIcon = {

                                RadioButton(
                                    selected = selectedIndex == 0,
                                    onClick = null
                                )
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text("Less Ingredients")
                            },

                            onClick = {

                                selectedIndex = 1
                                showDropdown = false

                                fevScreenViewModel.onEvent(
                                    FevRecipeScreen.Event.LessIngredientShort
                                )
                            },

                            leadingIcon = {

                                RadioButton(
                                    selected = selectedIndex == 1,
                                    onClick = null
                                )
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text("Reset")
                            },

                            onClick = {

                                selectedIndex = 2
                                showDropdown = false

                                fevScreenViewModel.onEvent(
                                    FevRecipeScreen.Event.ResetShort
                                )
                            },

                            leadingIcon = {

                                RadioButton(
                                    selected = selectedIndex == 2,
                                    onClick = null
                                )
                            }
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF605F5F),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White,
                    scrolledContainerColor = Color(0xFF605F5F)
                ),

                scrollBehavior = scrollBehavior
            )
        }

    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                        text = "Nothing Added Yet",
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {

                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        items(
                            items = uiState.data.orEmpty(),
                            key = { it.idMeal }
                        ) { item ->

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onClick(item.idMeal)
                                    },

                                shape = RoundedCornerShape(24.dp),

                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 8.dp
                                ),

                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFE0DFDF)
                                )
                            ) {

                                Column {

                                    AsyncImage(
                                        model = item.strMealThumb,
                                        contentDescription = item.strMeal,

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

                                        Spacer(
                                            modifier = Modifier.height(10.dp)
                                        )

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
