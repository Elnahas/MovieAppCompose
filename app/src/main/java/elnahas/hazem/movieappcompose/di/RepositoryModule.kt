package elnahas.hazem.movieappcompose.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import elnahas.hazem.movieappcompose.movieList.data.repository.MovieListRepositoryImpl
import elnahas.hazem.movieappcompose.movieList.domin.repository.MovieListRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        repository: MovieListRepositoryImpl
    ): MovieListRepository
}