package com.example.currencyconverterapp.ui.screens.converter.base_controller

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import com.example.currencyconverterapp.R

object BaseControllerHelpers {
    fun determineColorFilter(
        resource: Int,
        color: Color,
    ): ColorFilter? {
        return if (resource == R.drawable.flag) {
            ColorFilter.tint(color)
        } else {
            null
        }
    }

    @SuppressLint("DiscouragedApi")
    fun getFlagResourceByCurrencyCode(
        context: Context,
        code: String
    ): Int {
        val flagResourceId = context.resources.getIdentifier(
            code,
            "drawable",
            context.packageName,
        )
        return if (flagResourceId == 0) R.drawable.flag else flagResourceId
    }
}