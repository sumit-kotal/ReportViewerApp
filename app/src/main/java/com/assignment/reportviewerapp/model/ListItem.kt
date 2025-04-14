package com.assignment.reportviewerapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_items")
data class ListItem(
    @PrimaryKey val id: String,
    val name: String,
    val color: String?,
    val capacity: String?,
    val price: Double?,
    val generation: String?,
    val year: Int?,
    val cpuModel: String?,
    val hardDiskSize: String?
)