package com.example.theswitcher_nunosilva.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "division_table")
data class Division(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val switch: Boolean
)