package com.inlacou.exinput

import com.inlacou.exinput.utils.extensions.intergerNum
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    fun integer_digits() {
        assertEquals(2, "23,4".intergerNum(","))
        assertEquals(2, "23,45".intergerNum(","))
        assertEquals(2, "23,456".intergerNum(","))
        assertEquals(2, "23,".intergerNum(","))
        assertEquals(2, "23".intergerNum(","))
        assertEquals(3, "123".intergerNum(","))
        assertEquals(3, "123.".intergerNum(","))
        assertEquals(4, "123.0".intergerNum(","))
        assertEquals(5, "123.00".intergerNum(","))
    }
}