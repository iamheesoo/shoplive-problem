package com.example.shoplive_problem.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [CharacterEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CharacterDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var instance: CharacterDatabase? = null

        fun getInstance(context: Context): CharacterDatabase {
            return instance ?: synchronized(this) {
                buildDatabase(context)
                    .also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): CharacterDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                CharacterDatabase::class.java,
                "character_database"
            )
                .fallbackToDestructiveMigration() // migration이 실패하는 경우 db를 재생성 (모든 데이터가 유실될 수 있음)
                .build()
        }
    }

    abstract fun characterDao(): CharacterDao
}