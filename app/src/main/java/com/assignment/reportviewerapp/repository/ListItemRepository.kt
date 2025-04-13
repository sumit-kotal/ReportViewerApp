package com.assignment.reportviewerapp.repository

import android.util.Log
import com.assignment.reportviewerapp.model.ListItem
import com.assignment.reportviewerapp.repository.db.ListItemDao
import com.assignment.reportviewerapp.repository.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListItemRepository(
    private val listItemDao: ListItemDao
) {

    suspend fun fetchAndSaveListItems() {
        val response = RetrofitClient.apiService.getListItems()

        if (response.isSuccessful) {
            response.body()?.let {
                insertListItemsToDB(it)
            }
        } else {
            Log.e("ListItemRepository", "Error fetching items")
        }
    }

    private suspend fun insertListItemsToDB(listItems: List<ListItem>) {
        withContext(Dispatchers.IO) {
            listItems.forEach {
                listItemDao.insertListItem(it)
            }
        }
    }

    fun getAllListItems() = listItemDao.getAllListItems()

    suspend fun deleteItem(listItem: ListItem) {
        withContext(Dispatchers.IO) {
            listItemDao.deleteListItem(listItem)
        }
    }

    suspend fun updateItem(listItem: ListItem) {
        withContext(Dispatchers.IO) {
            listItemDao.updateListItem(listItem)
        }
    }

    suspend fun deleteAllItems() {
        withContext(Dispatchers.IO) {
            listItemDao.deleteAll()
        }
    }
}