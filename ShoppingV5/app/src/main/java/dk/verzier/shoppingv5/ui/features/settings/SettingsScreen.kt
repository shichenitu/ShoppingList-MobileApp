package dk.verzier.shoppingv5.ui.features.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dk.verzier.shoppingv5.R
import dk.verzier.shoppingv5.ui.components.ThemedPreviews
import dk.verzier.shoppingv5.ui.navigation.AppRoute
import dk.verzier.shoppingv5.ui.theme.ShoppingV5Theme
import kotlinx.serialization.Serializable

@Serializable
object Settings : AppRoute

@Composable
fun SettingsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.settings_screen_title),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@ThemedPreviews
@Composable
private fun SettingsScreenPreview() {
    ShoppingV5Theme {
        SettingsScreen()
    }
}