package dk.verzier.shoppingv4.domain

import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getShoppingList(): Flow<List<Item>>
    fun getItem(id: String): Flow<Item?>
    fun addItem(item: Item)
    fun removeItem(item: Item)
    fun updateItem(item: Item)
}