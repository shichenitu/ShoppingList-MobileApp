package dk.verzier.shoppingv4.ui.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dk.verzier.shoppingv4.R
import dk.verzier.shoppingv4.domain.Item
import dk.verzier.shoppingv4.domain.fullDescription
import dk.verzier.shoppingv4.ui.theme.ShoppingV4Theme
import kotlinx.serialization.Serializable

@Serializable
object ShoppingGraph

@Serializable
object ShoppingList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(
    onNavigate: (ShoppingListViewModel.NavigationEvent) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ShoppingListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.navigationEvents.collect {
            onNavigate(it)
        }
    }

    ShoppingListScreen(
        modifier = modifier,
        uiState = uiState,
        uiEvents = viewModel.uiEvents
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShoppingListScreen(
    uiState: ShoppingListViewModel.UiState,
    uiEvents: ShoppingListViewModel.UiEvents,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(modifier = Modifier.padding(vertical = 16.dp)) {
                    Text(
                        text = "Shopping list",
                        style = MaterialTheme.typography.titleLarge
                    )
                    uiState.shoppingList.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = item.fullDescription())
                            IconButton(onClick = { uiEvents.onEditItemClick(item) }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit item"
                                )
                            }
                        }
                    }
                }

                Button(onClick = uiEvents::onAddItemClick) {
                    Text(text = "Add item")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListScreenPreview() {
    ShoppingV4Theme {
        ShoppingListScreen(
            modifier = Modifier,
            uiState = ShoppingListViewModel.UiState(
                shoppingList = listOf(
                    Item(what = "Milk", where = "Dairy"),
                    Item(what = "Bread", where = "Bakery")
                )
            ),
            uiEvents = object : ShoppingListViewModel.UiEvents {
                override fun onAddItemClick() {}
                override fun onEditItemClick(item: Item) {}
            },
        )
    }
}