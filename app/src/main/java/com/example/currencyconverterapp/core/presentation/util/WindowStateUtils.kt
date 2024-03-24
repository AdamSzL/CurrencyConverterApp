package com.example.currencyconverterapp.core.presentation.util

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

fun getAdaptiveContentTypes(
    windowWidthSizeClass: WindowWidthSizeClass,
    windowHeightSizeClass: WindowHeightSizeClass,
): AdaptiveContentTypes {
    val navigationType = when (windowWidthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            CurrencyConverterNavigationType.BOTTOM_NAVIGATION
        }
        WindowWidthSizeClass.Medium -> {
            CurrencyConverterNavigationType.NAVIGATION_RAIL
        }
        WindowWidthSizeClass.Expanded -> {
            CurrencyConverterNavigationType.PERMANENT_NAVIGATION_DRAWER
        }
        else -> {
            CurrencyConverterNavigationType.BOTTOM_NAVIGATION
        }
    }

    val fabType = when (windowWidthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            FloatingActionButtonType.BOTTOM_RIGHT
        }
        else -> {
            FloatingActionButtonType.TOP_LEFT
        }
    }

    val topAppBarType = when (windowHeightSizeClass) {
        WindowHeightSizeClass.Compact -> {
            TopAppBarType.HIDDEN
        }
        else -> {
            TopAppBarType.VISIBLE
        }
    }

    val chartsControllerType = when (windowWidthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            ChartControllerType.VERTICAL
        }
        else -> {
            ChartControllerType.HORIZONTAL
        }
    }

    val chartsScreenContentType = when (windowHeightSizeClass) {
        WindowHeightSizeClass.Compact -> {
            ChartsScreenContentType.TABS
        }
        else -> {
            ChartsScreenContentType.FULL
        }
    }

    val conversionResultsListItemSize = when {
        windowHeightSizeClass == WindowHeightSizeClass.Expanded &&
                windowWidthSizeClass != WindowWidthSizeClass.Compact -> {
            ConversionResultsListItemSize.BIG
        }
        else -> {
            ConversionResultsListItemSize.DEFAULT
        }
    }

    val watchlistEntrySize = when {
        windowHeightSizeClass == WindowHeightSizeClass.Expanded &&
                windowWidthSizeClass != WindowWidthSizeClass.Compact -> {
            WatchlistEntrySize.BIG
        }
        else -> {
            WatchlistEntrySize.DEFAULT
        }
    }

    val converterAddCurrencyContainerType = when (windowWidthSizeClass) {
        WindowWidthSizeClass.Expanded -> {
            ConverterAddCurrencyContainerType.SIDE_PANEL
        }
        else -> {
            ConverterAddCurrencyContainerType.BOTTOM_SHEET
        }
    }

    val watchlistScreenContentType = when (windowWidthSizeClass) {
        WindowWidthSizeClass.Expanded -> {
            WatchlistScreenContentType.TWO_PANELS
        }
        else -> {
            WatchlistScreenContentType.ONE_PANEL
        }
    }

    return AdaptiveContentTypes(
        navigationType = navigationType,
        fabType = fabType,
        topAppBarType = topAppBarType,
        chartsControllerType = chartsControllerType,
        chartsScreenContentType = chartsScreenContentType,
        conversionResultsListItemSize = conversionResultsListItemSize,
        watchlistEntrySize = watchlistEntrySize,
        converterAddCurrencyContainerType = converterAddCurrencyContainerType,
        watchlistScreenContentType = watchlistScreenContentType,
    )
}

data class AdaptiveContentTypes(
    val navigationType: CurrencyConverterNavigationType,
    val fabType: FloatingActionButtonType,
    val topAppBarType: TopAppBarType,
    val chartsControllerType: ChartControllerType,
    val chartsScreenContentType: ChartsScreenContentType,
    val conversionResultsListItemSize: ConversionResultsListItemSize,
    val watchlistEntrySize: WatchlistEntrySize,
    val converterAddCurrencyContainerType: ConverterAddCurrencyContainerType,
    val watchlistScreenContentType: WatchlistScreenContentType,
)

enum class CurrencyConverterNavigationType {
    BOTTOM_NAVIGATION,
    NAVIGATION_RAIL,
    PERMANENT_NAVIGATION_DRAWER,
}

enum class FloatingActionButtonType {
    TOP_LEFT,
    BOTTOM_RIGHT
}

enum class TopAppBarType {
    HIDDEN,
    VISIBLE
}

enum class ChartControllerType {
    HORIZONTAL,
    VERTICAL
}

enum class ChartsScreenContentType {
    TABS,
    FULL
}

enum class ConversionResultsListItemSize {
    DEFAULT,
    BIG
}

enum class WatchlistEntrySize {
    DEFAULT,
    BIG
}

enum class ConverterAddCurrencyContainerType {
    SIDE_PANEL,
    BOTTOM_SHEET
}

enum class WatchlistScreenContentType {
    TWO_PANELS,
    ONE_PANEL
}