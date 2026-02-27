package dk.verzier.shoppingv5.domain

import kotlinx.coroutines.flow.Flow

interface ShopRepository {
    fun getShops(): Flow<List<Shop>>
}