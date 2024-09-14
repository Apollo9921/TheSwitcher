package com.example.theswitcher_nunosilva.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DivisionsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAllDivisions(divisions: List<Division>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDivision(division: Division)

    @Delete
    suspend fun deleteDivision(division: Division)

    @Query("SELECT * FROM division_table ORDER BY id ASC")
    fun readAllData(): List<Division>

    @Update
    suspend fun updateDivisionMode(division: Division)

}