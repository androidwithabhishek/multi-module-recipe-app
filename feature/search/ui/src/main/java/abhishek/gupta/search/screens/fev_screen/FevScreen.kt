package abhishek.gupta.search.screens.fev_screen

import abhishek.gupta.common.utils.UiText
import abhishek.gupta.search.screens.recipe_details.urlMaker
import android.R.attr.maxLines
import android.R.attr.onClick
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FevScreen(
    modifier: Modifier = Modifier,
    fevScreenViewModel: FevScreenViewModel,
    onClick: (id: String) -> Unit,
) {

    var showDropdown by rememberSaveable() {
        mutableStateOf(false)
    }

    var selectedIndex by rememberSaveable() {

        mutableStateOf(-0)
    }

    val uiState by fevScreenViewModel.fevUiState.collectAsState()
    val context = LocalContext.current

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()



    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.5.sp
                        )
                    )
                }, navigationIcon = {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.padding(start = 12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            modifier = Modifier.padding(end = 20.dp),
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF605F5F),
                    titleContentColor = Color.White,
                    scrolledContainerColor = Color(0xFF605F5F)
                ), scrollBehavior = scrollBehavior, actions = {
                    IconButton(onClick = { showDropdown = !showDropdown }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            modifier = Modifier.padding(end = 20.dp),
                            contentDescription = "MoreVert",
                            tint = Color.White
                        )
                    }
                    if (showDropdown) {
                        DropdownMenu(expanded = showDropdown, onDismissRequest = {
                            showDropdown = !showDropdown
                        }) {

                            DropdownMenuItem(text = { Text(text = "Alphabetical") }, onClick = {
                                selectedIndex = 0
                                showDropdown = !showDropdown
                                fevScreenViewModel.onEvent(
                                    FevRecipeScreen.Event.AlphabeticalShort
                                )
                            }, leadingIcon = {
                                RadioButton(
                                    selected = selectedIndex == 0,
                                    onClick = {
                                        selectedIndex = 0
                                        showDropdown = !showDropdown
                                        fevScreenViewModel.onEvent(
                                            FevRecipeScreen.Event.AlphabeticalShort
                                        )
                                    },

                                    )
                            })

                            DropdownMenuItem(text = { Text(text = "Less Ingredients") }, onClick = {
                                selectedIndex = 1
                                showDropdown = !showDropdown
                                fevScreenViewModel.onEvent(
                                    FevRecipeScreen.Event.LessIngredientShort
                                )
                            }, leadingIcon = {
                                RadioButton(
                                    selected = selectedIndex == 1,
                                    onClick = {
                                        selectedIndex = 1
                                        showDropdown = !showDropdown
                                        fevScreenViewModel.onEvent(
                                            FevRecipeScreen.Event.LessIngredientShort
                                        )
                                    },

                                    )
                            })

                            DropdownMenuItem(text = { Text(text = "Reset") }, onClick = {
                                selectedIndex = 2
                                showDropdown = !showDropdown
                                fevScreenViewModel.onEvent(
                                    FevRecipeScreen.Event.ResetShort
                                )
                            }, leadingIcon = {
                                RadioButton(
                                    selected = selectedIndex == 2,
                                    onClick = {
                                        selectedIndex = 2
                                        showDropdown = !showDropdown
                                        fevScreenViewModel.onEvent(
                                            FevRecipeScreen.Event.ResetShort
                                        )
                                    },

                                    )
                            })

                            DropdownMenuItem(text = { Text(text = "Alphabetical") }, onClick = {
                                selectedIndex = 0
                                showDropdown = !showDropdown
                                fevScreenViewModel.onEvent(
                                    FevRecipeScreen.Event.AlphabeticalShort
                                )
                            }, leadingIcon = {
                                RadioButton(
                                    selected = selectedIndex == 0,
                                    onClick = {
                                        selectedIndex = 0
                                        showDropdown = !showDropdown
                                        fevScreenViewModel.onEvent(
                                            FevRecipeScreen.Event.AlphabeticalShort
                                        )
                                    },

                                    )
                            })
                        }
                    }

                }
            )
        }) { paddingValues ->

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

                else -> {
                    if (uiState?.data?.isEmpty() == true){
                        Text(
                            text = "Nothing Added Yet",
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        items(uiState.data!!) { item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onClick.invoke(item.idMeal) },
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