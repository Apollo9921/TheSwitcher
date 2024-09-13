package com.example.theswitcher_nunosilva.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Division::class], version = 1, exportSchema = false)
abstract class DivisionDatabase: RoomDatabase() {

    abstract fun divisionDao(): DivisionsDAO

    companion object {
        @Volatile
        private var INSTANCE: DivisionDatabase? = null

        fun getDatabase(context: Context): DivisionDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DivisionDatabase::class.java,
                    "history_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}

suspend fun prepopulateDatabase(db: DivisionDatabase) {
    val initialData = listOf(
        Division(1, "Kitchen", false),
        Division(2, "Living room", false),
        Division(3, "Master bedroom", false),
        Division(4, "Guest’s bedroom", false)
    )
    db.divisionDao().addAllDivisions(initialData)
}