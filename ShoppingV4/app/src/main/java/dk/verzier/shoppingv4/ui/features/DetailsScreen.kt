package dk.verzier.shoppingv4.ui.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dk.verzier.shoppingv4.domain.Item
import dk.verzier.shoppingv4.ui.theme.ShoppingV4Theme
import kotlinx.serialization.Serializable

@Serializable
data class Details(val itemId: String)

@Composable
fun DetailsScreen(
    onNavigate: (DetailsViewModel.NavigationEvent) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.navigationEvents.collect {
            onNavigate(it)
        }
    }

    uiState.selectedItem?.let { item ->
        DetailsScreen(
            modifier = modifier,
            item = item,
            uiEvents = viewModel.uiEvents
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsScreen(
    item: Item,
    uiEvents: DetailsViewModel.UiEvents,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = uiEvents::onUpClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(text = "Edit item")

            TextButton(onClick = uiEvents::onSaveClick) {
                Text(text = "Save")
            }
        }

        Spacer(Modifier.height(height = 16.dp))

        TextField(
            value = item.what,
            onValueChange = uiEvents::onWhatChange,
            label = { Text(text = "What") }
        )

        Spacer(Modifier.height(height = 16.dp))

        TextField(
            value = item.where,
            onValueChange = uiEvents::onWhereChange,
            label = { Text(text = "Where") }
        )

        Spacer(Modifier.height(height = 16.dp))

        TextField(
            value = item.description,
            onValueChange = uiEvents::onDescriptionChange,
            label = { Text(text = "Description") },
            maxLines = 5
        )

        Spacer(Modifier.height(height = 16.dp))

        Button(onClick = uiEvents::onDeleteClick) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )

                Spacer(Modifier.height(height = 8.dp))

                Text(text = "Delete")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    ShoppingV4Theme {
        DetailsScreen(
            item = Item(what = "Milk", where = "Dairy", description = "Low fat"),
            uiEvents = object : DetailsViewModel.UiEvents {
                override fun onWhatChange(what: String) {}
                override fun onWhereChange(where: String) {}
                override fun onDescriptionChange(description: String) {}
                override fun onSaveClick() {}
                override fun onDeleteClick() {}
                override fun onUpClick() {}
            },
        )
    }
}