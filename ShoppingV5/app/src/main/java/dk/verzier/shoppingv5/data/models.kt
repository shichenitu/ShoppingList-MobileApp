package dk.verzier.shoppingv5.data

import androidx.compose.ui.graphics.Color
import dk.verzier.shoppingv5.domain.Item
import dk.verzier.shoppingv5.domain.Shop
import java.util.UUID
import androidx.core.graphics.toColorInt

data class ItemDto(
    val id: String = UUID.randomUUID().toString(),
    val what: String,
    val where: String,
    val description: String = ""
)

data class ShopDto(
    val name: String,
    val imageUrl: String,
    val brandColor: String
)

fun ItemDto.toItem(): Item = Item(id = this.id, what = this.what, where = this.where, description = this.description)
fun ShopDto.toShop(): Shop = Shop(name = this.name, imageUrl = this.imageUrl, brandColor = Color(
    color = this.brandColor.toColorInt()
))