package uk.ac.tees.mad.quoteverse.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.ac.tees.mad.quoteverse.data.local.FavoriteQuoteDao
import uk.ac.tees.mad.quoteverse.data.local.FavoriteQuoteDatabase
import uk.ac.tees.mad.quoteverse.data.local.FavoriteQuoteRepository
import uk.ac.tees.mad.quoteverse.data.local.FavoriteQuoteRepositoryImpl
import uk.ac.tees.mad.quoteverse.data.local.ThemePreferenceManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FavoriteQuoteDatabase {
        return FavoriteQuoteDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideFavoriteQuoteDao(database: FavoriteQuoteDatabase): FavoriteQuoteDao {
        return database.favoriteQuoteDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteQuoteRepository(dao: FavoriteQuoteDao): FavoriteQuoteRepository {
        return FavoriteQuoteRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideThemePreferenceManager(@ApplicationContext context: Context): ThemePreferenceManager {
        return ThemePreferenceManager(context)
    }
}