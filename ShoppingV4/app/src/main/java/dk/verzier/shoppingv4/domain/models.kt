package dk.verzier.shoppingv4.domain

import dk.verzier.shoppingv4.data.ItemDto
import java.util.UUID

data class Item(
    val id: String = UUID.randomUUID().toString(),
    val what: String,
    val where: String,
    val description: String = ""
)

fun Item.toDto(): ItemDto = ItemDto(id = this.id, what = this.what, where = this.where, description = this.description)

fun Item.fullDescription(): String = "Buy ${this.what.lowercase()} in: ${this.where}"