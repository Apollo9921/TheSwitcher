package com.example.theswitcher_nunosilva.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "division_table")
@Serializable
data class Division(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    var switch: Boolean
)