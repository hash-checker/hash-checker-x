package xyz.fartem.hashcheckerx.hash_generator.providers

import com.github.aelstad.keccakj.provider.KeccakjProvider
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import xyz.fartem.hashcheckerx.hash_generator.impl.DefaultHashGenerator
import xyz.fartem.hashcheckerx.hash_generator.impl.providers.keccakj.KeccakjHashProvider
import xyz.fartem.hashcheckerx.hash_generator.model.HashType
import java.security.Security

class KeccakjHashProviderTest {
    private val inputText = "Test"

    @Before
    fun setupSecurity() {
        Security.addProvider(KeccakjProvider())
    }

    @Test
    fun checkSHA3224FromString() {
        assertEquals(
            "d40cc4f9630f21eef0b185bdd6a51eab1775c1cd6ae458066ecaf046",
            getJdkHashCalculator().fromText(HashType.SHA3_224, inputText)
        )
    }

    private fun getJdkHashCalculator(): DefaultHashGenerator {
        return DefaultHashGenerator(
            listOf(
                KeccakjHashProvider(),
            ),
        )
    }

    @Test
    fun checkSHA3256FromString() {
        assertEquals(
            "c0a5cca43b8aa79eb50e3464bc839dd6fd414fae0ddf928ca23dcebf8a8b8dd0",
            getJdkHashCalculator().fromText(HashType.SHA3_256, inputText)
        )
    }

    @Test
    fun checkSHA3384FromString() {
        assertEquals(
            "da73bfcba560692a019f52c37de4d5e3ab49ca39c6a75594e3c39d805388c4de9d0ff3927eb9e197536f5b0b3a515f0a",
            getJdkHashCalculator().fromText(HashType.SHA3_384, inputText)
        )
    }

    @Test
    fun checkSHA3512FromString() {
        assertEquals(
            "301bb421c971fbb7ed01dcc3a9976ce53df034022ba982b97d0f27d48c4f03883aabf7c6bc778aa7c383062f6823045a6d41b8a720afbb8a9607690f89fbe1a7",
            getJdkHashCalculator().fromText(HashType.SHA3_512, inputText)
        )
    }
}
