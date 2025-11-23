package org.example

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class MainTest {
    @Test
    fun test1() {
        Assertions.assertTrue(true)
    }

    @Test
    fun test2() {
        Assertions.assertTrue(true)
        Main.main(null)
    }
}