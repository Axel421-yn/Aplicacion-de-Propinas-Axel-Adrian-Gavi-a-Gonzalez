package com.example.tiptime

import java.text.NumberFormat
import kotlin.math.ceil

// Accept either decimal separator, but no grouping separators.
internal fun parseNumber(input: String): Double = input.trim().replace(',', '.')
    .toDoubleOrNull()?.takeIf { it.isFinite() && it >= 0.0 } ?: 0.0

internal fun tipValue(amount: Double, tipPercent: Double, roundUp: Boolean): Double {
    if (!amount.isFinite() || !tipPercent.isFinite() || amount < 0 || tipPercent < 0) return 0.0
    val tip = tipPercent / 100 * amount
    if (!tip.isFinite()) return 0.0
    return if (roundUp) ceil(tip) else tip
}

internal fun calculateTip(amount: Double, tipPercent: Double = 15.0, roundUp: Boolean = false): String =
    NumberFormat.getCurrencyInstance().format(tipValue(amount, tipPercent, roundUp))
