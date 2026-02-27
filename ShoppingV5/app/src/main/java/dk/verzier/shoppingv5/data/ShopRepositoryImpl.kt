package dk.verzier.shoppingv5.data

import dk.verzier.shoppingv5.domain.Shop
import dk.verzier.shoppingv5.domain.ShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor() : ShopRepository {

    private val _shopList = MutableStateFlow(
        value = listOf(
            ShopDto(
                name = "Netto",
                imageUrl = "https://tse2.mm.bing.net/th/id/OIP.zLTC9xtHFbG3qlBlpreHLgHaHa",
                brandColor = "#fbdc12"
            ),
            ShopDto(
                name = "Rema1000",
                imageUrl = "https://logowik.com/content/uploads/images/rema-10007971.logowik.com.webp",
                brandColor = "#023ea5"
            ),
            ShopDto(
                name = "Kvickly",
                imageUrl = "https://via.ritzau.dk/data/images/00354/3718f3a8-2a42-4efd-b039-fde93c537ba7.png",
                brandColor = "#c50e20"
            ),
            ShopDto(
                name = "SuperBrugsen",
                imageUrl = "https://banner2.cleanpng.com/20181112/gog/kisspng-superbrugsen-esbjerg-leader-esbjerg-storcenter-dag-5bea05f7ef56e6.6004540715420636079803.jpg",
                brandColor = "#c31315"
            ),
            ShopDto(
                name = "365Discount",
                imageUrl = "https://tse1.mm.bing.net/th/id/OIP.HW3Q43OHW-EHjvYIi8XNnwHaHa",
                brandColor = "#086036"
            ),
            ShopDto(
                name = "Føtex",
                imageUrl = "https://www.legout.dk/wp-content/uploads/2024/02/275850_Ftex.png",
                brandColor = "#0e223b"
            ),
            ShopDto(
                name = "Bilka",
                imageUrl = "https://foodpartners.dk/wp-content/uploads/2021/10/bilka_logo.png",
                brandColor = "#009fe3"
            ),
            ShopDto(
                name = "Hart Bakery",
                imageUrl = "https://images.squarespace-cdn.com/content/v1/53a0099de4b03f568ef67cea/1596556236454-82QYXKM4AXJ2L1DPTAOF/Hart-logo.png",
                brandColor = "#000000"
            )
        )
    )

    override fun getShops(): Flow<List<Shop>> {
        return _shopList.map { dtoList -> dtoList.map { it.toShop() } }
    }
}