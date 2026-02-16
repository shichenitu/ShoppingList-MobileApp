package dk.verzier.shoppingv3.data

import dk.verzier.shoppingv3.domain.Item
import dk.verzier.shoppingv3.domain.ItemRepository
import dk.verzier.shoppingv3.domain.toDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepositoryImpl @Inject constructor() : ItemRepository {

    private val _shoppingList = MutableStateFlow(
        value = listOf(
            ItemDto(what = "Coffee", where = "Føtex"),
            ItemDto(what = "Carrots", where = "Netto"),
            ItemDto(what = "Milk", where = "Super Brugsen"),
            ItemDto(what = "Bread", where = "Hart Bakery"),
            ItemDto(what = "Butter", where = "Føtex")
        )
    )

    override fun getShoppingList(): Flow<List<Item>> {
        return _shoppingList.map { dtoList ->
            dtoList.sortedByWhereAndWhat().map { it.toItem() }
        }
    }

    override fun addItem(item: Item) {
        val formattedItemDto =
            item.copy(what = item.what.toTitleCase(), where = item.where.toTitleCase()).toDto()

        _shoppingList.update { currentList ->
            if (currentList.contains(formattedItemDto)) {
                currentList
            } else {
                currentList + formattedItemDto
            }
        }
    }

    override fun removeItem(item: Item) {
        _shoppingList.update { currentList ->
            currentList - item.toDto()
        }
    }

    private fun String.toTitleCase(): String {
        return this.trim().split(" ").joinToString(separator = " ") { word ->
            word.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
    }

    private fun List<ItemDto>.sortedByWhereAndWhat() =
        this.sortedWith(
            comparator = compareBy(
                { it.where },
                { it.what }
            )
        )
}