package dk.verzier.shoppingv5.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dk.verzier.shoppingv5.domain.ItemRepository
import dk.verzier.shoppingv5.domain.ShopRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindingsModule {

    @Singleton
    @Binds
    abstract fun bindItemRepository(impl: ItemRepositoryImpl): ItemRepository

    @Singleton
    @Binds
    abstract fun bindShopRepository(impl: ShopRepositoryImpl): ShopRepository
}