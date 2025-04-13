package com.assignment.reportviewerapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.reportviewerapp.model.ListItem
import com.assignment.reportviewerapp.repository.ListItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListViewModel(private val repository: ListItemRepository) : ViewModel() {
    private val _listItems = MutableStateFlow<List<ListItem>>(emptyList())
    val listItems: StateFlow<List<ListItem>> = _listItems

    private val _operationState = MutableStateFlow<OperationState>(OperationState.Idle)
    val operationState: StateFlow<OperationState> = _operationState

    fun resetOperationState() {
        _operationState.value = OperationState.Idle
    }

    init {
        loadItems()
    }

    private fun loadItems() {
        _operationState.value = OperationState.Loading
        viewModelScope.launch {
            try {
                repository.getAllListItems().collect { items ->
                    _listItems.value = items
                    if (items.isEmpty()) {
                        fetchFromApi()
                    }
                }
            } catch (e: Exception) {
                Log.e("ListViewModel", "Error loading items", e)
                _operationState.value = OperationState.Error("Failed to load items")
            } finally {
                if (_listItems.value.isNotEmpty()) {
                    _operationState.value = OperationState.Success("Items loaded")
                }
            }
        }
    }

    private suspend fun fetchFromApi() {
        _operationState.value = OperationState.Loading
        try {
            repository.fetchAndSaveListItems()
            _operationState.value = OperationState.Success("Items loaded from API")
        } catch (e: Exception) {
            Log.e("ListViewModel", "Error fetching from API", e)
            _operationState.value = OperationState.Error("Failed to fetch items from API")
        }
    }

    fun deleteItem(item: ListItem) {
        viewModelScope.launch {
            _operationState.value = OperationState.Loading
            try {
                repository.deleteItem(item)
                _operationState.value = OperationState.Success("Item deleted")
            } catch (e: Exception) {
                Log.e("ListViewModel", "Error deleting item", e)
                _operationState.value = OperationState.Error("Failed to delete item")
            }
        }
    }

    fun updateItem(item: ListItem) {
        viewModelScope.launch {
            _operationState.value = OperationState.Loading
            try {
                repository.updateItem(item)
                _operationState.value = OperationState.Success("Item updated")
            } catch (e: Exception) {
                Log.e("ListViewModel", "Error updating item", e)
                _operationState.value = OperationState.Error("Failed to update item")
            }
        }
    }
}

sealed class OperationState {
    object Idle : OperationState()
    object Loading : OperationState()
    data class Success(val message: String) : OperationState()
    data class Error(val message: String) : OperationState()
}