package com.example.to_docompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.example.to_docompose.data.models.ToDoTask

/**
 *  Database for Quests
 */

@Database(
    entities = [ToDoTask::class],
    version = 3, // Incremented version
    exportSchema = true
)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao

    companion object {
        const val DATABASE_NAME = "todo_database"

        // Migration from version 1 to 2
        val MIGRATION_1_2 = Migration(1, 2) { database ->
            database.execSQL("ALTER TABLE todo_table ADD COLUMN isCompleted INTEGER NOT NULL DEFAULT 0")
        }

        // Migration from version 2 to 3
        val MIGRATION_2_3 = Migration(2, 3) { database ->
            database.execSQL("ALTER TABLE todo_table ADD COLUMN isQuickBoard INTEGER NOT NULL DEFAULT 0")
        }
    }
}
