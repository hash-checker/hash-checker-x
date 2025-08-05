package xyz.fartem.hashcheckerx.hash_generator.jdk

import org.junit.Assert.assertEquals
import org.junit.Test
import xyz.fartem.hashcheckerx.hash_generator.impl.DefaultHashGenerator
import xyz.fartem.hashcheckerx.hash_generator.impl.providers.jdk.JdkHashProvider
import xyz.fartem.hashcheckerx.hash_generator.model.HashType

class JdkHashProviderTest {
    private val inputText = "Test"

    @Test
    fun checkMD5FromString() {
        assertEquals(
            "0cbc6611f5540bd0809a388dc95a615b",
            getJdkHashCalculatorFor(HashType.MD5).fromText(inputText)
        )
    }

    private fun getJdkHashCalculatorFor(hashType: HashType): DefaultHashGenerator {
        return DefaultHashGenerator(
            listOf(
                JdkHashProvider(),
            ),
            hashType,
        )
    }

    @Test
    fun checkSHA1FromString() {
        assertEquals(
            "640ab2bae07bedc4c163f679a746f7ab7fb5d1fa",
            getJdkHashCalculatorFor(HashType.SHA_1).fromText(inputText)
        )
    }

    @Test
    fun checkSHA224FromString() {
        assertEquals(
            "3606346815fd4d491a92649905a40da025d8cf15f095136b19f37923",
            getJdkHashCalculatorFor(HashType.SHA_224).fromText(inputText)
        )
    }

    @Test
    fun checkSHA256FromString() {
        assertEquals(
            "532eaabd9574880dbf76b9b8cc00832c20a6ec113d682299550d7a6e0f345e25",
            getJdkHashCalculatorFor(HashType.SHA_256).fromText(inputText)
        )
    }

    @Test
    fun checkSHA384FromString() {
        assertEquals(
            "7b8f4654076b80eb963911f19cfad1aaf4285ed48e826f6cde1b01a79aa73fadb5446e667fc4f90417782c91270540f3",
            getJdkHashCalculatorFor(HashType.SHA_384).fromText(inputText)
        )
    }

    @Test
    fun checkSHA512FromString() {
        assertEquals(
            "c6ee9e33cf5c6715a1d148fd73f7318884b41adcb916021e2bc0e800a5c5dd97f5142178f6ae88c8fdd98e1afb0ce4c8d2c54b5f37b30b7da1997bb33b0b8a31",
            getJdkHashCalculatorFor(HashType.SHA_512).fromText(inputText)
        )
    }
}
