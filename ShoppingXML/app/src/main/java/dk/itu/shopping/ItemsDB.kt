package dk.itu.shopping

class ItemsDB {
    val items = ArrayList<Item>()

    init {
        fillItemsDB()
    }

    companion object {
        private var sItemsDB: ItemsDB? = null

        fun get(): ItemsDB {
            return sItemsDB ?: ItemsDB().also { sItemsDB = it }
        }
    }

    fun addItem(what: String, where: String) {
        items.add(Item(what, where))
    }

    fun removeItem(what: String, where: String) {
        items.removeIf { it.what == what && it.where == where }
    }

    fun size(): Int {
        return items.size
    }

    private fun fillItemsDB() {
        items.add(Item("coffee", "Irma"))
        items.add(Item("carrots", "Netto"))
        items.add(Item("milk", "Netto"))
        items.add(Item("bread", "bakery"))
        items.add(Item("butter", "Irma"))
        items.add(Item("apples", "Netto"))
        items.add(Item("oranges", "Netto"))
        items.add(Item("cheese", "Netto"))
        items.add(Item("juice", "Føtex"))
        items.add(Item("onions", "Netto"))
        items.add(Item("potatoes", "Menu"))
        items.add(Item("chips", "Netto"))
        items.add(Item("coke", "Liedl"))
        items.add(Item("soap", "Netto"))
        items.add(Item("cookies", "Irma"))
        items.add(Item("ham", "Irma"))
    }
}