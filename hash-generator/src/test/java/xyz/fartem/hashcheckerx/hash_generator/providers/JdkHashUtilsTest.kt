package xyz.fartem.hashcheckerx.hash_generator.providers

import org.junit.Assert.assertEquals
import org.junit.Test
import xyz.fartem.hashcheckerx.hash_generator.impl.DefaultHashUtils

class JdkHashUtilsTest {
    @Test
    fun checkStringFromBytesArray() {
        val input = byteArrayOf(
            12, -68, 102, 17,
            -11, 84, 11, -48,
            -128, -102, 56, -115,
            -55, 90, 97, 91
        )

        assertEquals(
            "0cbc6611f5540bd0809a388dc95a615b",
            DefaultHashUtils.getStringFromByteArray(input)
        )
    }

    @Test
    fun checkStringFromLong() {
        assertEquals(
            "784dd132",
            DefaultHashUtils.getStringFromLong(2018365746)
        )
    }
}
