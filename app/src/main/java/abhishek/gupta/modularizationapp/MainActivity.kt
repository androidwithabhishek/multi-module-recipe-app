package abhishek.gupta.modularizationapp

import abhishek.gupta.modularizationapp.navigation.NavigationSubGraphs
import abhishek.gupta.modularizationapp.navigation.RecipeNavigation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import abhishek.gupta.modularizationapp.ui.theme.ModularizationAppTheme
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Surface
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationSubGraphs: NavigationSubGraphs


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            ModularizationAppTheme {
//
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//
//
//            }
            Surface(modifier =Modifier.fillMaxSize()) {
                RecipeNavigation(
                    modifier = Modifier,
                    navigationSubGraphs = navigationSubGraphs
                )
            }
        }
    }


    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ModularizationAppTheme {
            Greeting("Android")
        }
    }
}