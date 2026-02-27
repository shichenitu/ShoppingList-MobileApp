package dk.verzier.shoppingv4.data

import dk.verzier.shoppingv4.domain.Item
import java.util.UUID

data class ItemDto(
    val id: String = UUID.randomUUID().toString(),
    val what: String,
    val where: String,
    val description: String = ""
)

fun ItemDto.toItem(): Item = Item(id = this.id, what = this.what, where = this.where, description = this.description)