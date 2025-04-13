package com.assignment.reportviewerapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.assignment.reportviewerapp.model.ListItem
import com.assignment.reportviewerapp.viewmodel.ListViewModel
import com.assignment.reportviewerapp.viewmodel.OperationState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListScreen(
    navController: NavController,
    viewModel: ListViewModel = koinViewModel()
) {
    val listItems by viewModel.listItems.collectAsStateWithLifecycle()
    val operationState by viewModel.operationState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var showPopup by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<ListItem?>(null) }

    // Handle operation states
    LaunchedEffect(operationState) {
        when (val state = operationState) {
            is OperationState.Success -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                viewModel.resetOperationState()
            }
            is OperationState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                viewModel.resetOperationState()
            }
            OperationState.Idle -> {}
            OperationState.Loading -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(listItems) { item ->
                ListItemCard(
                    item = item,
                    onUpdate = {
                        selectedItem = it
                        showPopup = true
                    },
                    onDelete = { viewModel.deleteItem(it) }
                )
            }
        }

        // Update Dialog
        if (showPopup && selectedItem != null) {
            UpdateItemDialog(
                item = selectedItem!!,
                onDismiss = { showPopup = false },
                onUpdate = { updatedItem ->
                    viewModel.updateItem(updatedItem)
                    showPopup = false
                }
            )
        }
    }
}

@Composable
fun UpdateItemDialog(
    item: ListItem,
    onDismiss: () -> Unit,
    onUpdate: (ListItem) -> Unit
) {
    var updatedItem by remember { mutableStateOf(item) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Update Item", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = updatedItem.name,
                    onValueChange = { updatedItem = updatedItem.copy(name = it) },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = updatedItem.color ?: "",
                    onValueChange = { updatedItem = updatedItem.copy(color = it) },
                    label = { Text("Color") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = updatedItem.capacity ?: "",
                    onValueChange = { updatedItem = updatedItem.copy(capacity = it) },
                    label = { Text("Capacity") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = { onUpdate(updatedItem) }
                    ) {
                        Text("Update")
                    }
                }
            }
        }
    }
}

@Composable
fun ListItemCard(
    item: ListItem,
    onUpdate: (ListItem) -> Unit,
    onDelete: (ListItem) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onUpdate(item) },  // Show update popup on click
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(item.name, style = MaterialTheme.typography.bodyLarge)
            item.color?.let { Text("Color: $it", style = MaterialTheme.typography.bodySmall) }
            item.capacity?.let { Text("Capacity: $it", style = MaterialTheme.typography.bodySmall) }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Button(
                    onClick = { onUpdate(item) },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Save")
                }
                Button(
                    onClick = { onDelete(item) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Close")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    ListScreen(navController = rememberNavController())
}