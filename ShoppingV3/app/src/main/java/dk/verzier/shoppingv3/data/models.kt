package dk.verzier.shoppingv3.data

import dk.verzier.shoppingv3.domain.Item


data class ItemDto(val what: String, val where: String)

fun ItemDto.toItem(): Item = Item(what = this.what, where = this.where)