package dk.verzier.shoppingv2.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.verzier.shoppingv2.ItemsDB
import dk.verzier.shoppingv2.R
import dk.verzier.shoppingv2.ui.theme.ShoppingV2Theme
import kotlinx.coroutines.launch

@Composable
fun ShoppingListScreen(modifier: Modifier = Modifier, snackbarHostState: SnackbarHostState) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var showShoppingList: Boolean by remember { mutableStateOf(value = false) }
        var what by remember { mutableStateOf("") }
        var where by remember { mutableStateOf("") }

        TextField(
            value = what,
            onValueChange = { what = it },
            label = { Text(text = stringResource(id = R.string.what_label)) }
        )
        Spacer(Modifier.height(16.dp))
        TextField(
            value = where,
            onValueChange = { where = it },
            label = { Text(text = stringResource(id = R.string.where_label)) }
        )

        if (showShoppingList) {
            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                Text(text = stringResource(id = R.string.list_label), style = typography.titleLarge)
                val scope = rememberCoroutineScope()
                val snackbarActionLabel = stringResource(id = R.string.undo_label)
                ItemsDB.shoppingList.forEach { item ->
                    val snackbarMessage =
                        stringResource(id = R.string.item_removed_label, item.what, item.where)
                    Text(
                        text = stringResource(
                            id = R.string.list_item_label, item.what.lowercase(), item.where
                        ),
                        modifier = Modifier.clickable {
                            ItemsDB.removeItem(item)
                            scope.launch {
                                val result = snackbarHostState
                                    .showSnackbar(
                                        message = snackbarMessage,
                                        actionLabel = snackbarActionLabel,
                                        duration = SnackbarDuration.Short
                                    )
                                when (result) {
                                    SnackbarResult.ActionPerformed -> {
                                        /* Handle snackbar action performed (UNDO) */
                                    }

                                    SnackbarResult.Dismissed -> {
                                        /* Handle snackbar dismissed */
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = {}) {
                Text(text = stringResource(id = R.string.add_button_label))
            }
            Button(onClick = { showShoppingList = !showShoppingList }) {
                Text(text = stringResource(id = if (!showShoppingList) R.string.list_button_label else R.string.hide_button_label))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListScreenPreview() {
    ShoppingV2Theme {
        val snackbarHostState = remember { SnackbarHostState() }
        ShoppingListScreen(modifier = Modifier, snackbarHostState = snackbarHostState)
    }
}