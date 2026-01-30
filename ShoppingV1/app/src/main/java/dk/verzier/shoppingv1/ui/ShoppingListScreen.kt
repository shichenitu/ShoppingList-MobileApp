package dk.verzier.shoppingv1.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.verzier.shoppingv1.R
import dk.verzier.shoppingv1.ui.theme.ShoppingV1Theme

@Composable
fun ShoppingListScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var showShoppingList: Boolean by remember { mutableStateOf(value = false) }

        val shoppingList: List<String> = listOf(
            "Buy coffee in: Føtex",
            "Buy carrots in: Netto",
            "Buy milk in: Super Brugsen",
            "Buy bread in: Hart Bakery",
            "Buy butter in: Føtex"
        )

        if (showShoppingList) {
            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                Text(text = stringResource(id = R.string.list_label), style = typography.titleLarge)
                shoppingList.forEach { item ->
                    Text(text = item)
                }
            }
        }

        Button(onClick = { showShoppingList = false }) {
            Text(text = stringResource(id = R.string.list_button_label))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListScreenPreview() {
    ShoppingV1Theme {
        ShoppingListScreen()
    }
}