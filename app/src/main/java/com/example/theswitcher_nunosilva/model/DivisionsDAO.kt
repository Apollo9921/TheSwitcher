package com.example.theswitcher_nunosilva.model

import androidx.room.Dao
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

    @Query("DELETE FROM division_table WHERE id = :id")
    suspend fun deleteDivision(id: Int)

    @Query("SELECT * FROM division_table ORDER BY id ASC")
    fun readAllData(): List<Division>

    @Update
    suspend fun updateDivisionMode(division: Division)

}