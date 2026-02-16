package dk.verzier.shoppingv3.domain

import android.content.Context
import dk.verzier.shoppingv3.R
import dk.verzier.shoppingv3.data.ItemDto

data class Item(val what: String, val where: String)

fun Item.toDto(): ItemDto = ItemDto(what = this.what, where = this.where)

fun Item.fullDescription(context: Context): String =
    context.getString(R.string.list_item_label, this.what.lowercase(), this.where)