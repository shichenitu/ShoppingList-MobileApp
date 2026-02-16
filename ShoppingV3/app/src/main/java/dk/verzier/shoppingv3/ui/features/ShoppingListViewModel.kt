package dk.verzier.shoppingv3.ui.features

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.verzier.shoppingv3.R
import dk.verzier.shoppingv3.domain.Item
import dk.verzier.shoppingv3.domain.ItemRepository
import dk.verzier.shoppingv3.ui.components.SnackBarHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    itemRepository: ItemRepository,
    snackBarHandler: SnackBarHandler
) : ViewModel() {
    private val shoppingListVisibility: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    private val shoppingList: StateFlow<List<Item>> =
        itemRepository.getShoppingList()
            .stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = emptyList()
            )

    val uiState: StateFlow<UiState> = combine(
        flow = shoppingList,
        flow2 = shoppingListVisibility
    ) { currentShoppingList, currentListVisibility ->
        UiState(
            shoppingList = currentShoppingList,
            displayShoppingList = currentListVisibility
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(stopTimeoutMillis = 500),
        initialValue = UiState()
    )

    val uiEvents = object : UiEvents {
        override fun onAddItemClick(
            itemWhat: String,
            itemWhere: String,
        ) {
            if (itemWhat.isNotBlank() && itemWhere.isNotBlank()) {
                itemRepository.addItem(Item(what = itemWhat.trim(), where = itemWhere.trim()))
            } else {
                snackBarHandler.postMessage(msgRes = R.string.textfield_error_message)
            }
        }

        override fun onRemoveItemClick(item: Item) {
            itemRepository.removeItem(item)
            snackBarHandler.postMessage(
                msgRes = R.string.item_removed_label,
                item.what,
                item.where,
                actionLabelRes = R.string.undo_label,
                onDismiss = { },
                onActionClick = {
                    itemRepository.addItem(item)
                    snackBarHandler.postMessage(msgRes = R.string.undo_confirmation_message)
                }
            )
        }

        override fun onToggleListVisibilityClick() {
            shoppingListVisibility.update { !it }
        }
    }

    data class UiState(
        val shoppingList: List<Item> = emptyList(),
        val displayShoppingList: Boolean = false,
        @get:StringRes val toggleListVisibilityButtonLabel: Int = R.string.list_button_label
    )

    @Immutable
    interface UiEvents {
        fun onAddItemClick(itemWhat: String, itemWhere: String)
        fun onRemoveItemClick(item: Item)
        fun onToggleListVisibilityClick()
    }
}