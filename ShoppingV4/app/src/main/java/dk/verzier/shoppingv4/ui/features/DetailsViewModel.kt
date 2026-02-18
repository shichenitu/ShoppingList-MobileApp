package dk.verzier.shoppingv4.ui.features

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.verzier.shoppingv4.domain.Item
import dk.verzier.shoppingv4.domain.ItemRepository
import dk.verzier.shoppingv4.ui.components.SnackBarHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val snackBarHandler: SnackBarHandler,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val detailsArgs: Details = savedStateHandle.toRoute()
    private val itemId: String = detailsArgs.itemId

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    init {
        viewModelScope.launch {
            itemRepository.getItem(id = itemId).collect { item ->
                _uiState.update { it.copy(selectedItem = item) }
            }
        }
    }

    val uiEvents: UiEvents = object : UiEvents {

        override fun onWhatChange(what: String) {
            _uiState.update { it.copy(selectedItem = it.selectedItem?.copy(what = what)) }
        }

        override fun onWhereChange(where: String) {
            _uiState.update { it.copy(selectedItem = it.selectedItem?.copy(where = where)) }
        }

        override fun onDescriptionChange(description: String) {
            _uiState.update { it.copy(selectedItem = it.selectedItem?.copy(description = description)) }
        }

        override fun onSaveClick() {
            _uiState.value.selectedItem?.let { item ->
                itemRepository.updateItem(item = item)
            }
            onUpClick()
        }

        override fun onDeleteClick() {
            _uiState.value.selectedItem?.let { item ->
                itemRepository.removeItem(item)
                snackBarHandler.postMessage(
                    msg = "Item \"${item.what}\" from \"${item.where}\" was removed",
                    actionLabel = "Undo",
                    onDismiss = { },
                    onActionClick = {
                        itemRepository.addItem(item)
                        snackBarHandler.postMessage(msg = "The deletion was undone")
                    }
                )
            }
            onUpClick()
        }

        override fun onUpClick() {
            viewModelScope.launch {
                _navigationEvents.emit(value = NavigationEvent.NavigateUp)
            }
        }
    }

    data class UiState(val selectedItem: Item? = null)

    @Immutable
    interface UiEvents {
        fun onWhatChange(what: String)
        fun onWhereChange(where: String)
        fun onDescriptionChange(description: String)
        fun onSaveClick()
        fun onDeleteClick()
        fun onUpClick()
    }

    sealed class NavigationEvent {
        data object NavigateUp : NavigationEvent()
    }
}