package dk.verzier.shoppingv5.ui.features.shoppinglist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dk.verzier.shoppingv5.R
import dk.verzier.shoppingv5.ui.components.NavigationType
import dk.verzier.shoppingv5.ui.components.ShoppingTextField
import dk.verzier.shoppingv5.ui.components.ShoppingTopAppBar
import dk.verzier.shoppingv5.ui.components.ThemedPreviews
import dk.verzier.shoppingv5.ui.navigation.AppRoute
import dk.verzier.shoppingv5.ui.theme.ShoppingV5Theme
import kotlinx.serialization.Serializable

@Serializable
object AddWhat : AppRoute

@Composable
fun AddWhatScreen(
    onNavigate: (AddWhatViewModel.NavigationEvent) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddWhatViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.navigationEvents.collect {
            onNavigate(it)
        }
    }

    AddWhatScreen(
        modifier = modifier,
        uiState = uiState,
        uiEvents = viewModel.uiEvents
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddWhatScreen(
    uiState: AddWhatViewModel.UiState,
    uiEvents: AddWhatViewModel.UiEvents,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            ShoppingTopAppBar(
                titleRes = R.string.add_what_title,
                navigationType = NavigationType.BACK,
                onUpClick = uiEvents::onUpClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ShoppingTextField(
                value = uiState.what,
                onValueChange = uiEvents::onWhatChange,
                labelRes = R.string.what_label,
                focusManager = LocalFocusManager.current,
                isLastField = false,
                isError = uiState.isError
            )

            Spacer(Modifier.height(height = 8.dp))

            Button(onClick = uiEvents::onNextClick) {
                Text(text = stringResource(id = R.string.next_button_label))
            }
        }
    }
}

@ThemedPreviews
@Composable
fun AddWhatScreenPreview() {
    ShoppingV5Theme {
        AddWhatScreen(
            modifier = Modifier,
            uiState = AddWhatViewModel.UiState(what = "Milk"),
            uiEvents = object : AddWhatViewModel.UiEvents {
                override fun onWhatChange(what: String) {}
                override fun onNextClick() {}
                override fun onUpClick() {}
            }
        )
    }
}