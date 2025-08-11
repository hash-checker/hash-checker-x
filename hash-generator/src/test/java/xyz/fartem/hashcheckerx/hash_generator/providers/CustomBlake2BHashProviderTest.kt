package xyz.fartem.hashcheckerx.hash_generator.providers

import org.junit.Assert.assertEquals
import org.junit.Test
import xyz.fartem.hashcheckerx.hash_generator.impl.DefaultHashGenerator
import xyz.fartem.hashcheckerx.hash_generator.impl.providers.custom.blake2b.CustomBlake2BHashProvider
import xyz.fartem.hashcheckerx.hash_generator.model.HashType

class CustomBlake2BHashProviderTest {
    private val inputText = "Test"

    @Test
    fun checkBlake2BFromText() {
        assertEquals(
            "3d896914f86ae22c48b06140adb4492fa3f8e2686a83cec0c8b1dcd6903168751370078bbd6bbfe02a6ab1df12a19b5991b58e65e243ec279f6a5770b2dd0e31",
            DefaultHashGenerator(
                listOf(
                    CustomBlake2BHashProvider()
                )
            ).fromText(HashType.BLAKE2b, inputText)
        )
    }
}
