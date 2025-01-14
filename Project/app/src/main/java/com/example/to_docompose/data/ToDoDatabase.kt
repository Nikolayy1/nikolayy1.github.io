package com.example.to_docompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.to_docompose.data.models.ToDoTask

@Database(
    entities = [ToDoTask::class],
    version = 2, // Incremented version
    exportSchema = true
)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao

    companion object {
        const val DATABASE_NAME = "todo_database"

        val MIGRATION_1_2 = androidx.room.migration.Migration(1, 2) { database ->
            database.execSQL("ALTER TABLE todo_table ADD COLUMN isCompleted INTEGER NOT NULL DEFAULT 0")
        }
    }
}
