package abhishek.gupta.search.di

import abhishek.gupta.search.navigaton.SearchFeatureApi
import abhishek.gupta.search.navigaton.SearchFeatureApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UiModule {
    @Provides
    fun searchFeatureApi(): SearchFeatureApi {
        return SearchFeatureApiImpl()
    }
}