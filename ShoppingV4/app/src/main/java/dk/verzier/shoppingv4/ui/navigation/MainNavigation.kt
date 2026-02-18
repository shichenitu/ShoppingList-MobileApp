package dk.verzier.shoppingv4.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import dk.verzier.shoppingv4.ui.features.AddWhat
import dk.verzier.shoppingv4.ui.features.AddWhatScreen
import dk.verzier.shoppingv4.ui.features.AddWhatViewModel
import dk.verzier.shoppingv4.ui.features.AddWhere
import dk.verzier.shoppingv4.ui.features.AddWhereScreen
import dk.verzier.shoppingv4.ui.features.AddWhereViewModel
import dk.verzier.shoppingv4.ui.features.Details
import dk.verzier.shoppingv4.ui.features.DetailsScreen
import dk.verzier.shoppingv4.ui.features.DetailsViewModel
import dk.verzier.shoppingv4.ui.features.ShoppingGraph
import dk.verzier.shoppingv4.ui.features.ShoppingList
import dk.verzier.shoppingv4.ui.features.ShoppingListScreen
import dk.verzier.shoppingv4.ui.features.ShoppingListViewModel

@Composable
fun MainNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val singleTopNavOptions: NavOptions = navOptions {
        launchSingleTop = true
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ShoppingGraph
    ) {
        navigation<ShoppingGraph>(startDestination = ShoppingList) {
            composable<ShoppingList> {
                ShoppingListScreen(
                    onNavigate = { event ->
                        when (event) {
                            is ShoppingListViewModel.NavigationEvent.NavigateToAddWhat -> navController.navigate(
                                route = AddWhat,
                                navOptions = singleTopNavOptions
                            )

                            is ShoppingListViewModel.NavigationEvent.NavigateToDetails -> {
                                // TODO add navigation to DetailsScreen logic
                            }
                        }
                    }
                )
            }
            composable<Details>(
                deepLinks = listOf(
                    // TODO add deep linking matching the following URI pattern: "shopping://items/{itemId}" - check the intent-filter in the AndroidManifest.xml file
                ),
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(durationMillis = 500)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(durationMillis = 500)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(durationMillis = 500)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(durationMillis = 500)
                    )
                }
            ) {
                DetailsScreen(
                    onNavigate = { event ->
                        when (event) {
                            is DetailsViewModel.NavigationEvent.NavigateUp -> {
                                // TODO add back button logic
                            }
                        }
                    }
                )
            }
        }
        composable<AddWhat>(
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = 500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(durationMillis = 500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = 500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(durationMillis = 500)
                )
            }
        ) {
            AddWhatScreen(
                onNavigate = { event ->
                    when (event) {
                        is AddWhatViewModel.NavigationEvent.NavigateUp -> {
                            // TODO add back button logic
                        }

                        is AddWhatViewModel.NavigationEvent.NavigateToAddWhere -> navController.navigate(
                            route = AddWhere(event.what),
                            navOptions = singleTopNavOptions
                        )
                    }
                }
            )
        }
        dialog<AddWhere> {
            AddWhereScreen(
                onNavigate = { event ->
                    when (event) {
                        is AddWhereViewModel.NavigationEvent.CloseDialog -> navController.popBackStack()
                        is AddWhereViewModel.NavigationEvent.NavigateToShoppingList -> navController.popBackStack(
                            route = ShoppingGraph,
                            inclusive = false
                        )
                    }
                }
            )
        }
    }
}