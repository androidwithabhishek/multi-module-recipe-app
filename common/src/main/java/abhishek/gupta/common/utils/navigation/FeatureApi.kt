package abhishek.gupta.common.utils.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController


interface FeatureApi {

    abstract fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController,
    )


}