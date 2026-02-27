package dk.verzier.shoppingv4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import dk.verzier.shoppingv4.ui.components.SnackBarHandler
import dk.verzier.shoppingv4.ui.navigation.MainNavigation
import dk.verzier.shoppingv4.ui.theme.ShoppingV4Theme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var snackBarHandler: SnackBarHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingV4Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackBarHandler.snackBarHostState
                        ) { snackBarData: SnackbarData ->
                            Snackbar(snackbarData = snackBarData)
                        }
                    }) { _ ->
                    MainNavigation()
                }
            }
        }
    }
}
