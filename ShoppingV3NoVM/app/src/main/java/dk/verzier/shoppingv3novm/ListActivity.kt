package dk.verzier.shoppingv3novm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import dk.verzier.shoppingv3novm.ui.Item
import dk.verzier.shoppingv3novm.ui.ShoppingListScreen
import dk.verzier.shoppingv3novm.ui.theme.ShoppingV3NoVMTheme

class ListActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingV3NoVMTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val context = LocalContext.current

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = stringResource(id = R.string.app_name))
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = colorScheme.primaryContainer
                            ),
                            navigationIcon = {
                                IconButton(onClick = {
                                    val intent =
                                        Intent(/* packageContext = */ context, /* cls = */
                                            MainActivity::class.java)
                                    context.startActivity(/* p0 = */ intent)
                                }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            }
                        )
                    },
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }) { innerPadding ->
                    ShoppingListScreen(
                        modifier = Modifier.padding(paddingValues = innerPadding),
                        snackbarHostState = snackbarHostState,
                        itemToAdd = intent.getStringExtra("EXTRA_WHAT")?.let { what ->
                            intent.getStringExtra("EXTRA_WHERE")?.let { where ->
                                Item(what = what, where = where)
                            }
                        }
                    )
                }
            }
        }
    }
}