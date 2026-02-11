package dk.verzier.shoppingv3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dagger.hilt.android.AndroidEntryPoint
import dk.verzier.shoppingv3.ui.components.SnackBarHandler
import dk.verzier.shoppingv3.ui.features.ShoppingListScreen
import dk.verzier.shoppingv3.ui.theme.ShoppingV3Theme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var snackBarHandler: SnackBarHandler

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingV3Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(stringResource(R.string.app_name))
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = colorScheme.primaryContainer
                            )
                        )
                    },
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackBarHandler.snackBarHostState
                        ) { snackBarData: SnackbarData ->
                            Snackbar(snackbarData = snackBarData)
                        }
                    }) { innerPadding ->
                    ShoppingListScreen(
                        modifier = Modifier.padding(paddingValues = innerPadding),
                    )
                }
            }
        }
    }
}
