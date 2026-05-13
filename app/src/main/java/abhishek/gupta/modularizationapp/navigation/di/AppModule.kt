package abhishek.gupta.modularizationapp.navigation.di

import abhishek.gupta.common.utils.navigation.FeatureApi
import abhishek.gupta.common.utils.navigation.NavigationSubGraphRoutes
import abhishek.gupta.modularizationapp.navigation.NavigationSubGraphs
import abhishek.gupta.search.navigaton.SearchFeatureApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun navigationSubGraphs(searchFeatureApi: SearchFeatureApi): NavigationSubGraphs {
                   return NavigationSubGraphs (searchFeatureApi)

    }
}