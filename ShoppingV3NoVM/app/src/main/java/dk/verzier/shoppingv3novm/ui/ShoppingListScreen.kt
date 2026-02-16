package dk.verzier.shoppingv3novm.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.verzier.shoppingv3novm.R
import dk.verzier.shoppingv3novm.ui.theme.ShoppingV3NoVMTheme
import kotlinx.coroutines.launch

data class Item(val what: String, val where: String)

// Extension function to handle Title Case formatting
private fun String.toTitleCase(): String {
    return this.trim().split(" ").joinToString(" ") { word ->
        word.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}

@Composable
fun ShoppingListScreen(modifier: Modifier = Modifier, snackbarHostState: SnackbarHostState, itemToAdd: Item?) {

    val shoppingList = remember {
        mutableStateListOf(
            Item(what = "Coffee", where = "Føtex"),
            Item(what = "Carrots", where = "Netto"),
            Item(what = "Milk", where = "Super Brugsen"),
            Item(what = "Bread", where = "Hart Bakery"),
            Item(what = "Butter", where = "Føtex")
        )
    }

    fun addItem(item: Item) {
        val formattedItem =
            item.copy(what = item.what.toTitleCase(), where = item.where.toTitleCase())

        if (!shoppingList.contains(formattedItem)) {
            shoppingList.add(formattedItem)
        }
    }

    var hasProcessedIntent by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (itemToAdd != null && !hasProcessedIntent) {
            addItem(itemToAdd)
            hasProcessedIntent = true
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.list_label),
            style = typography.titleLarge
        )
        val snackbarActionLabel = stringResource(id = R.string.undo_label)
        val scope = rememberCoroutineScope()
        val undoConfirmationMessage = stringResource(R.string.undo_confirmation_message)
        shoppingList.forEach { item ->
            val snackbarMessage =
                stringResource(id = R.string.item_removed_label, item.what, item.where)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(
                        id = R.string.list_item_label, item.what.lowercase(), item.where
                    ),
                    modifier = Modifier.padding(start = 14.dp).clickable {
                        scope.launch {
                            val result = snackbarHostState
                                .showSnackbar(
                                    message = snackbarMessage,
                                    actionLabel = snackbarActionLabel,
                                    duration = SnackbarDuration.Short
                                )
                            when (result) {
                                SnackbarResult.ActionPerformed -> {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(undoConfirmationMessage)
                                    }
                                }

                                SnackbarResult.Dismissed -> {
                                    shoppingList.remove(element = item)
                                }
                            }
                        }
                    }
                )
                IconButton(modifier = Modifier.padding(end = 14.dp), onClick = { /* Handle email to a friend action */ }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListScreenPreview() {
    ShoppingV3NoVMTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        AddItemScreen(
            modifier = Modifier,
            snackbarHostState = snackbarHostState
        )
    }
}