package dk.verzier.shoppingv3.ui.features

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dk.verzier.shoppingv3.R
import dk.verzier.shoppingv3.domain.Item
import dk.verzier.shoppingv3.domain.fullDescription
import dk.verzier.shoppingv3.ui.theme.ShoppingV3Theme

@Composable
fun ShoppingListScreen(
    modifier: Modifier = Modifier,
    viewModel: ShoppingListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ShoppingListScreen(modifier, uiState, viewModel.uiEvents)
}

@Composable
private fun ShoppingListScreen(
    modifier: Modifier = Modifier,
    uiState: ShoppingListViewModel.UiState,
    uiEvents: ShoppingListViewModel.UiEvents
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var what by remember { mutableStateOf(value = "") }
        var where by remember { mutableStateOf(value = "") }
        val focusManager = LocalFocusManager.current

        TextField(
            value = what,
            onValueChange = { what = it },
            label = { Text(text = stringResource(id = R.string.what_label)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
        )
        Spacer(Modifier.height(16.dp))
        TextField(
            value = where,
            onValueChange = { where = it },
            label = { Text(text = stringResource(id = R.string.where_label)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )

        if (uiState.displayShoppingList) {
            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                Text(
                    text = stringResource(id = R.string.list_label),
                    style = typography.titleLarge
                )
                val context = LocalContext.current
                uiState.shoppingList.forEach { item ->
                    Text(
                        text = item.fullDescription(context),
                        modifier = Modifier.clickable { uiEvents.onRemoveItemClick(item) }
                    )
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = {
                uiEvents.onAddItemClick(itemWhat = what, itemWhere = where)
                what = ""
                where = ""
                focusManager.clearFocus()
            }) {
                Text(text = stringResource(id = R.string.add_button_label))
            }
            Button(onClick = uiEvents::onToggleListVisibilityClick) {
                Text(text = stringResource(id = if (!uiState.displayShoppingList) R.string.list_button_label else R.string.hide_button_label))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListScreenPreview() {
    ShoppingV3Theme {
        ShoppingListScreen(
            modifier = Modifier,
            uiState = ShoppingListViewModel.UiState(),
            uiEvents = object : ShoppingListViewModel.UiEvents {
                override fun onAddItemClick(itemWhat: String, itemWhere: String) {}
                override fun onRemoveItemClick(item: Item) {}
                override fun onToggleListVisibilityClick() {}
            }
        )
    }
}