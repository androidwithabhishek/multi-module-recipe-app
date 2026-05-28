package abhishek.gupta.search.screens.recipe_details

import abhishek.gupta.common.utils.UiText
import abhishek.gupta.common.utils.navigation.NavigationRoutes
import abhishek.gupta.search.domain.model.DomainRecipeDetails
import android.text.Layout
import android.widget.Toast
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.collectLatest
import java.time.format.TextStyle


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipeDetailsViewModel: RecipeDetailsViewModel,
    navHostController: NavHostController,
    onNavigationClick: () -> Unit,
    onDelete: (id: String) -> Unit,
    onLike: (domainRecipeDetail: DomainRecipeDetails) -> Unit,


    ) {

    val uiState by recipeDetailsViewModel.uiStateForDetails.collectAsState()

    val context = LocalContext.current

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()


    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = recipeDetailsViewModel.navigation) {

        recipeDetailsViewModel.navigation.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { navigation ->

                when (navigation) {
                    RecipeDetails.Navigation.GoBack -> {
                        navHostController.popBackStack()
                    }

                    is RecipeDetails.Navigation.GoFav -> {

                        navHostController.navigate(NavigationRoutes.FavoriteScreen.routes)

                    }
                }
            }


    }

//    Tost
    LaunchedEffect(Unit) {

        recipeDetailsViewModel.uiEvent.collect { uiEvent ->

            when(uiEvent) {

                is RecipeDetails.UiEvent.ShowToast -> {

                    Toast.makeText(
                        context,
                        uiEvent .message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

//    val systemUiController = rememberSystemUiController()

//    SideEffect {
//
//        systemUiController.setStatusBarColor(
//            color = Color(0xFF605F5F),
//            darkIcons = true
//        )
//    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {


        if (uiState.isLoading) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                item {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .background(Color.LightGray.copy(alpha = 0.4f))
                    )
                }

                item {

                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray.copy(alpha = 0.4f))
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                                .height(20.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray.copy(alpha = 0.3f))
                        )

                        Spacer(modifier = Modifier.height(30.dp))

                        repeat(6) {

                            Row(
                                modifier = Modifier.padding(vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape)
                                        .background(Color.LightGray.copy(alpha = 0.4f))
                                )

                                Spacer(modifier = Modifier.width(14.dp))

                                Column {

                                    Box(
                                        modifier = Modifier
                                            .width(140.dp)
                                            .height(18.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color.LightGray.copy(alpha = 0.4f))
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Box(
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(14.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color.LightGray.copy(alpha = 0.25f))
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        if (uiState.error !is UiText.None) {
            Text(
                text = uiState.error.getString(context),
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        }


        uiState.data?.let {

            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = it.strMeal,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = androidx.compose.ui.text.TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 0.5.sp
                                )
                            )
                        }, navigationIcon = {
                            IconButton(
                                onClick = { onNavigationClick.invoke() },
                                modifier = Modifier.padding(start = 12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Color.White
                                )
                            }
                        }, actions = {

                            Row(
                                modifier = Modifier.padding(end = 20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                IconButton(onClick = { onLike.invoke(it) }) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }

//                                IconButton(onClick = { onDelete.invoke(it.idMeal)  }) {
//                                    Icon(
//                                        modifier = Modifier.padding(start = 16.dp),
//                                        imageVector = Icons.Default.Delete,
//                                        contentDescription = null,
//                                        tint = Color.White
//                                    )
//                                }
                            }

                        }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color(0xFF605F5F),
                            titleContentColor = Color.White,
                            scrolledContainerColor = Color(0xFF605F5F)
                        ), scrollBehavior = scrollBehavior
                    )
                }) { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(Color.White)
                ) {

                    item {

                        AsyncImage(
                            model = it.strMealThumb,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    item {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {

                            Text(
                                text = it.strMeal,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = it.strCategory, fontSize = 18.sp, color = Color.Gray
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Instructions",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = it.strInstructions,
                                fontSize = 16.sp,
                                lineHeight = 26.sp,
                                color = Color.DarkGray
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Ingredients", fontSize = 22.sp, fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    item {

                        it.ingredientsPair.forEach {

                            if (it.first.isNotBlank() || it.second.isNotBlank()) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp, vertical = 8.dp),

                                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    AsyncImage(
                                        model = urlMaker(it.first),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(CircleShape)
                                            .background(Color.LightGray),

                                        contentScale = ContentScale.Crop
                                    )

                                    Column {

                                        Text(
                                            text = it.first,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        )

                                        Text(
                                            text = it.second, color = Color.Gray
                                        )
                                    }
                                }
                            }

                        }


                    }

                    item {

                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "Watch Youtube Video",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(30.dp))
                        }
                    }
                }

            }
        }


    }

}


fun urlMaker(name: String): String {

    val Url: String = "https://www.themealdb.com/images/ingredients/${name}.png"
    return Url
}


