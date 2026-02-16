package dk.verzier.shoppingv2

import androidx.compose.runtime.mutableStateListOf

data class Item(val what: String, val where: String)

object ItemsDB {
    private val _shoppingList = mutableStateListOf(
        Item(what = "Coffee", where = "Føtex"),
        Item(what = "Carrots", where = "Netto"),
        Item(what = "Milk", where = "Super Brugsen"),
        Item(what = "Bread", where = "Hart Bakery"),
        Item(what = "Butter", where = "Føtex")
    )

    val shoppingList: List<Item> get() = _shoppingList.toList()

    // Extension function to handle Title Case formatting
    private fun String.toTitleCase(): String {
        return this.trim().split(" ").joinToString(" ") { word ->
            word.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
    }

    fun addItem(item: Item) {
        val formattedItem =
            item.copy(what = item.what.toTitleCase(), where = item.where.toTitleCase())

        _shoppingList.add(formattedItem)
    }

    fun removeItem(item: Item) {
        _shoppingList.remove(element = item)
    }
}