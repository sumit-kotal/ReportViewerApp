package com.assignment.reportviewerapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_items")
data class ListItem(
    @PrimaryKey val id: String, // id is a string in the API response
    val name: String,
    val color: String?, // color as a field, some items may not have color
    val capacity: String?, // capacity as a field, some items may not have capacity
    val price: Double?, // price as a field, some items may not have price
    val generation: String?, // generation as a field, if present
    val year: Int?, // year as a field, if present
    val cpuModel: String?, // cpu model as a field, if present
    val hardDiskSize: String? // hard disk size as a field, if present
)