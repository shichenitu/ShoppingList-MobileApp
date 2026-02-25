package dk.verzier.shoppingv5.data

import dk.verzier.shoppingv5.domain.Item
import dk.verzier.shoppingv5.domain.ItemRepository
import dk.verzier.shoppingv5.domain.toDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepositoryImpl @Inject constructor() : ItemRepository {

    private val _shoppingList = MutableStateFlow(
        value = listOf(
            ItemDto(
                id = UUID.randomUUID().toString(),
                what = "Coffee",
                where = "Føtex",
                description = "The one Bob showed us - with the blue label."
            ),
            ItemDto(
                id = UUID.randomUUID().toString(),
                what = "Carrots",
                where = "Netto",
                description = ""
            ),
            ItemDto(
                id = UUID.randomUUID().toString(),
                what = "Milk",
                where = "SuperBrugsen",
                description = "Both sødmælk and lactose-free minimælk"
            ),
            ItemDto(
                id = "deep-link-item",
                what = "Bread",
                where = "Hart Bakery",
                description = "Half-a-loaf and a chocolate bun (please!)"
            ),
            ItemDto(
                id = UUID.randomUUID().toString(),
                what = "Butter",
                where = "Føtex",
                description = ""
            )
        )
    )

    override fun getShoppingList(): Flow<List<Item>> {
        return _shoppingList.map { dtoList ->
            dtoList.sortedByWhereAndWhat().map { it.toItem() }.toSet().toList()
        }
    }

    override fun getItem(id: String): Flow<Item?> {
        return _shoppingList.map { dtoList ->
            dtoList.find { it.id == id }?.toItem()
        }
    }

    override fun addItem(item: Item) {
        val formattedItemDto =
            item.copy(what = item.what.toTitleCase(), where = item.where.toTitleCase()).toDto()

        _shoppingList.update { currentList ->
            if (currentList.any { it.what == formattedItemDto.what && it.where == formattedItemDto.where }) {
                currentList
            } else {
                currentList + formattedItemDto
            }
        }
    }

    override fun removeItem(item: Item) {
        _shoppingList.update { currentList ->
            currentList.filter { it.id != item.id }
        }
    }

    override fun updateItem(item: Item) {
        val formattedItemDto =
            item.copy(what = item.what.toTitleCase(), where = item.where.toTitleCase()).toDto()

        _shoppingList.update { currentList ->
            currentList.map {
                if (it.id == formattedItemDto.id) {
                    formattedItemDto
                } else {
                    it
                }
            }
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