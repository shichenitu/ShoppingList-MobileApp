package dk.verzier.shoppingv3novm.ui

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.verzier.shoppingv3novm.ListActivity
import dk.verzier.shoppingv3novm.R
import dk.verzier.shoppingv3novm.ui.theme.ShoppingV3NoVMTheme
import kotlinx.coroutines.launch

@Composable
fun AddItemScreen(modifier: Modifier = Modifier, snackbarHostState: SnackbarHostState) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var what by remember { mutableStateOf(value = "") }
        var where by remember { mutableStateOf(value = "") }
        val context = LocalContext.current

        TextField(
            value = what,
            onValueChange = { what = it },
            label = { Text(text = stringResource(id = R.string.what_label)) }
        )
        Spacer(Modifier.height(height = 16.dp))
        TextField(
            value = where,
            onValueChange = { where = it },
            label = { Text(text = stringResource(id = R.string.where_label)) }
        )
        Spacer(Modifier.height(height = 16.dp))

        val scope = rememberCoroutineScope()

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            val snackbarMessage = stringResource(id = R.string.textfield_error_message)
            Button(onClick = {
                if (what.isNotBlank() && where.isNotBlank()) {
                    // Add missing behaviour

                    val activity = context as? android.app.Activity
                    val resultIntent = Intent().apply {
                        putExtra("EXTRA_WHAT", what)
                        putExtra("EXTRA_WHERE", where)
                    }
                    activity?.setResult(android.app.Activity.RESULT_OK, resultIntent)
                    activity?.finish()
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar(snackbarMessage)
                    }
                }
            }) {
                Text(text = stringResource(id = R.string.add_button_label))
            }
            val context = LocalContext.current
            Button(onClick = {
                val intent = Intent(/* packageContext = */ context, /* cls = */ ListActivity::class.java).apply {
                    putExtra("EXTRA_WHAT", what)
                    putExtra("EXTRA_WHERE", where)
                }
                context.startActivity(/* p0 = */ intent)
            }) {
                Text(text = stringResource(id = R.string.list_button_label))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddItemScreenPreview() {
    ShoppingV3NoVMTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        AddItemScreen(
            modifier = Modifier,
            snackbarHostState = snackbarHostState
        )
    }
}