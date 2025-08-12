package xyz.fartem.hashcheckerx.hash_generator.providers

import org.junit.Assert.assertEquals
import org.junit.Test
import xyz.fartem.hashcheckerx.hash_generator.api.HashGenerator
import xyz.fartem.hashcheckerx.hash_generator.impl.DefaultHashGenerator
import xyz.fartem.hashcheckerx.hash_generator.impl.providers.custom.fnv1a.CustomFNV1aHashProvider
import xyz.fartem.hashcheckerx.hash_generator.model.HashType

class CustomFNV1aHashProviderTest {
    private val inputText = "Test"

    @Test
    fun checkFNV1a32FromText() {
        assertEquals(
            "2ffcbe05",
            getHashGenerator().fromText(HashType.FNV_1A_32, inputText)
        )
    }

    private fun getHashGenerator(): HashGenerator {
        return DefaultHashGenerator(
            listOf(
                CustomFNV1aHashProvider()
            )
        )
    }

    @Test
    fun checkFNV1a64FromText() {
        assertEquals(
            "2474e7fb1aec9f05",
            getHashGenerator().fromText(HashType.FNV_1A_64, inputText)
        )
    }

    @Test
    fun checkFNV1a128FromText() {
        assertEquals(
            "68e554f9c5757277b806e94c1fb4fcc5",
            getHashGenerator().fromText(HashType.FNV_1A_128, inputText)
        )
    }

    @Test
    fun checkFNV1a256FromText() {
        assertEquals(
            "e46ddd4ed460b28c4ece66459f2a8e9d123f79d831721584cc463c5a98bd4c65",
            getHashGenerator().fromText(HashType.FNV_1A_256, inputText)
        )
    }

    @Test
    fun checkFNV1a512FromText() {
        assertEquals(
            "f9fe9eefe38ca43fcf36c8fbc0d25bef51797ddeec00000000002a5259a146c7f24cae042d99828e5baba0a28b18bf530de9c3137ca2a36973f8d0786c7072b5",
            getHashGenerator().fromText(HashType.FNV_1A_512, inputText)
        )
    }

    @Test
    fun checkFNV1a1024FromText() {
        assertEquals(
            "26f791f9147aedad1354bef7d238f3219005cbd6e8d664f6b4eefdbe94929e41548c17f600860000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001ba08046e07e0418fb7be0ec07b8ea87a61bb4f073e2bab740db8398ef60cb9b50bff5f3dd1081",
            getHashGenerator().fromText(HashType.FNV_1A_1024, inputText)
        )
    }
}
