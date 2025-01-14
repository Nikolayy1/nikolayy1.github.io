package com.example.to_docompose.di

import android.content.Context
import androidx.room.Room
import com.example.to_docompose.data.ToDoDao
import com.example.to_docompose.data.ToDoDatabase
import com.example.to_docompose.data.ToDoDatabase.Companion.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ToDoDatabase {
        return Room.databaseBuilder(
            context,
            ToDoDatabase::class.java,
            ToDoDatabase.DATABASE_NAME
        )
            .addMigrations(ToDoDatabase.MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideToDoDao(database: ToDoDatabase): ToDoDao {
        return database.toDoDao()
    }
}
