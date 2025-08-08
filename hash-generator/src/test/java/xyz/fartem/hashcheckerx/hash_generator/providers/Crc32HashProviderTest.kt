package xyz.fartem.hashcheckerx.hash_generator.providers

import org.junit.Assert.assertEquals
import org.junit.Test
import xyz.fartem.hashcheckerx.hash_generator.impl.DefaultHashGenerator
import xyz.fartem.hashcheckerx.hash_generator.impl.providers.crc32.Crc32HashProvider
import xyz.fartem.hashcheckerx.hash_generator.model.HashType

class Crc32HashProviderTest {
    private val inputText = "Test"

    @Test
    fun checkCRC32FromText() {
        assertEquals(
            "784dd132",
            DefaultHashGenerator(
                listOf(
                    Crc32HashProvider()
                )
            ).fromText(HashType.CRC_32, inputText)
        )
    }
}
