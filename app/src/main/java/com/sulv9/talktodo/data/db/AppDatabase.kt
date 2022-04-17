package com.sulv9.talktodo.data.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sulv9.talktodo.data.model.Todo
import com.sulv9.talktodo.data.model.TodoGroup

@Database(
    version = 1,
    entities = [Todo::class, TodoGroup::class],
//    autoMigrations = [
//        AutoMigration(from = 1, to = 2)
//    ],
//    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            instance?.let { return it }
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "talkTodo.db"
            ).fallbackToDestructiveMigration().build().also { instance = it }
        }
    }
}