package org.example

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class Main3Test {
    @Test
    fun test31() {
        Assertions.assertTrue(true)
    }

    @Test
    fun test32() {
        Assertions.assertTrue(true)
        Main2.main(null)
    }
}