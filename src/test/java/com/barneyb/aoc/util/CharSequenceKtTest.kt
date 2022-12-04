package com.barneyb.aoc.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CharSequenceKtTest {

    @Test
    fun toInt() {
        assertEquals(0, Slice("0").toInt())
        assertEquals(1, Slice("1").toInt())
        assertEquals(12, Slice("12").toInt())
        assertEquals(12300, Slice("12300").toInt())
        assertEquals(-42, Slice("-42").toInt())
        assertThrows(NumberFormatException::class.java) {
            Slice("cow").toInt()
        }
    }

    @Test
    fun camelToTitle() {
        assertAll(
            // simple
            { assertEquals("Cow Dog Horse", "CowDogHorse".camelToTitle()) },
            // suffix
            { assertEquals("My File", "MyFileKt".camelToTitle()) },
            // acronym
            { assertEquals("The FBI Watches", "TheFBIWatches".camelToTitle()) },
            { assertEquals("The Fbi Watches", "TheFbiWatches".camelToTitle()) },
            // stopword
            { assertEquals("I Am a Boat", "IAmABoat".camelToTitle()) },
        )
    }
}
