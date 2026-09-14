package com.example.tiptime

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Locale

class TipCalculatorTest {
    @Test fun customPercentage() { assertEquals(20.0, tipValue(100.0, 20.0, false), 0.000001) }
    @Test fun fractionalTip() { assertEquals(10.65, tipValue(71.0, 15.0, false), 0.000001) }
    @Test fun roundUpFraction() { assertEquals(11.0, tipValue(71.0, 15.0, true), 0.0) }
    @Test fun roundUpWholeNumber() { assertEquals(20.0, tipValue(100.0, 20.0, true), 0.0) }
    @Test fun zeroPercentage() { assertEquals(0.0, tipValue(100.0, 0.0, true), 0.0) }
    @Test fun emptyInput() { assertEquals(0.0, parseNumber(""), 0.0) }
    @Test fun commaDecimal() { assertEquals(125.5, parseNumber("125,50"), 0.0) }
    @Test fun dotDecimal() { assertEquals(125.5, parseNumber("125.50"), 0.0) }
    @Test fun invalidInput() { assertEquals(0.0, parseNumber("abc"), 0.0) }
    @Test fun negativeInput() { assertEquals(0.0, parseNumber("-20"), 0.0) }
    @Test fun nonFiniteInput() { assertEquals(0.0, parseNumber("NaN"), 0.0) }
    @Test fun overflowDoesNotDisplayInfinity() {
        assertEquals(0.0, tipValue(Double.MAX_VALUE, Double.MAX_VALUE, false), 0.0)
    }
    @Test fun currencyFormat() {
        val original = Locale.getDefault()
        try {
            Locale.setDefault(Locale.US)
            assertEquals("$10.65", calculateTip(71.0, 15.0, false))
        } finally { Locale.setDefault(original) }
    }
}
