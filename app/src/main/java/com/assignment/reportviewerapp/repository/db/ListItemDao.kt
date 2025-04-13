package com.assignment.reportviewerapp.repository.db

import androidx.room.*
import com.assignment.reportviewerapp.model.ListItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ListItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListItem(listItem: ListItem)

    @Update
    suspend fun updateListItem(listItem: ListItem)

    @Delete
    suspend fun deleteListItem(listItem: ListItem)

    @Query("SELECT * FROM list_items")
    fun getAllListItems(): Flow<List<ListItem>>

    @Query("DELETE FROM list_items")
    suspend fun deleteAll()
}