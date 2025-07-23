package xyz.fartem.hashcheckerx.hash_generator.jdk

import org.junit.Assert.assertEquals
import org.junit.Test
import xyz.fartem.hashcheckerx.hash_generator.impl.jdk.JdkHashUtils

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
            JdkHashUtils.getStringFromByteArray(input)
        )
    }

    @Test
    fun checkStringFromLong() {
        assertEquals(
            "784dd132",
            JdkHashUtils.getStringFromLong(2018365746)
        )
    }
}