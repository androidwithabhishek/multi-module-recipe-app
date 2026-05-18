package abhishek.gupta.search.data.di

import abhishek.gupta.search.data.remote.SearchApiService
import abhishek.gupta.search.data.repositoryImpl.SearchRepositoryImpl
import abhishek.gupta.search.domain.repository.SearchRepository
import abhishek.gupta.search.local.AppDB
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


const val BASE_URL = "https://www.themealdb.com/"

@InstallIn(SingletonComponent::class)
@Module
object SearchDataModule {


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()


    }

    @Provides
    fun provideSearchApiService(retrofit: Retrofit): SearchApiService {
        return retrofit.create(SearchApiService::class.java)

    }


    @Provides
    fun provideSearchRepository(searchApiService: SearchApiService): SearchRepository {
        return SearchRepositoryImpl(searchApiService)

    }

    @Provides
    @Singleton
    fun providesAppDB(@ApplicationContext context: Context): AppDB {
        return AppDB.getInstance(context)
    }



}

//@Module
//abstract class Network{
//
//    @Binds
//    abstract fun provideSearchRepository1(searchApiService: SearchApiService): SearchRepository
//}



