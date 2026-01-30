package dk.verzier.shoppingv1

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dk.verzier.shoppingv1.ui.ShoppingListScreen
import dk.verzier.shoppingv1.ui.theme.ShoppingV1Theme

class MainActivity : ComponentActivity() {
    // Helper function to easily show a Toast
    private fun showToast(message: String) {
        Toast.makeText(
            /* context = */ this,
            /* text = */ message,
            /* duration = */ Toast.LENGTH_SHORT
        ).show()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showToast("LIFECYCLE: onCreate()")
        Log.d("LIFECYCLE", "onCreate()")
        enableEdgeToEdge()
        setContent {
            ShoppingV1Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = stringResource(id = R.string.app_name))
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = colorScheme.primaryContainer
                            )
                        )
                    }) { innerPadding ->
                    ShoppingListScreen(
                        modifier = Modifier.padding(paddingValues = innerPadding)
                    )
                }
            }
        }
    }
}