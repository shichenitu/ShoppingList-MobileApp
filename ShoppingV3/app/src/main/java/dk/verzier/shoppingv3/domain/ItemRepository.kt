package dk.verzier.shoppingv3.domain

import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getShoppingList(): Flow<List<Item>>
    fun addItem(item: Item)
    fun removeItem(item: Item)
}