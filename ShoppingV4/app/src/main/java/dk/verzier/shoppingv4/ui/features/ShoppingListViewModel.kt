package dk.verzier.shoppingv4.ui.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.verzier.shoppingv4.domain.Item
import dk.verzier.shoppingv4.domain.ItemRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.annotation.concurrent.Immutable
import javax.inject.Inject
import kotlin.String

@HiltViewModel
class ShoppingListViewModel @Inject constructor(private val itemRepository: ItemRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(value = UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    init {
        viewModelScope.launch {
            itemRepository.getShoppingList().collect { newList ->
                _uiState.update { it.copy(shoppingList = newList) }
            }
        }
    }

    val uiEvents: UiEvents = object : UiEvents {
        override fun onAddItemClick() {
            viewModelScope.launch {
                _navigationEvents.emit(value = NavigationEvent.NavigateToAddWhat)
            }
        }

        override fun onEditItemClick(item: Item) {
            viewModelScope.launch {
                // TODO add navigation to DetailsScreen logic
                _navigationEvents.emit(value = NavigationEvent.NavigateToDetails(itemId = item.id))
            }
        }
    }

    data class UiState(val shoppingList: List<Item> = emptyList())

    @Immutable
    interface UiEvents {
        fun onAddItemClick()
        fun onEditItemClick(item: Item)
    }

    sealed class NavigationEvent {
        data object NavigateToAddWhat : NavigationEvent()
        data class NavigateToDetails(val itemId: String) : NavigationEvent()
    }
}