package xyz.fartem.hashcheckerx.hash_generator.jdk

import com.github.aelstad.keccakj.provider.KeccakjProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import xyz.fartem.hashcheckerx.hash_generator.impl.jdk.JdkHashGeneratorDigest
import xyz.fartem.hashcheckerx.hash_generator.model.HashType
import java.security.Security
import kotlin.test.BeforeTest

class JdkHashGeneratorDigestTest {
    private val input = "Test".toByteArray()

    @BeforeTest
    fun setupSecurity() {
        Security.addProvider(KeccakjProvider())
    }

    @Test
    fun checkMD5HashDigest() {
        val jdkHashCalculatorDigest = JdkHashGeneratorDigest.instanceFor(HashType.MD5)
        jdkHashCalculatorDigest.update(input)

        assertEquals(
            "0cbc6611f5540bd0809a388dc95a615b",
            jdkHashCalculatorDigest.result()
        )
    }

    @Test
    fun checkSHA1HashDigest() {
        val jdkHashCalculatorDigest = JdkHashGeneratorDigest.instanceFor(HashType.SHA_1)
        jdkHashCalculatorDigest.update(input)

        assertEquals(
            "640ab2bae07bedc4c163f679a746f7ab7fb5d1fa",
            jdkHashCalculatorDigest.result()
        )
    }

    @Test
    fun checkSHA224HashDigest() {
        val jdkHashCalculatorDigest = JdkHashGeneratorDigest.instanceFor(HashType.SHA_224)
        jdkHashCalculatorDigest.update(input)

        assertEquals(
            "3606346815fd4d491a92649905a40da025d8cf15f095136b19f37923",
            jdkHashCalculatorDigest.result()
        )
    }

    @Test
    fun checkSHA256HashDigest() {
        val jdkHashCalculatorDigest = JdkHashGeneratorDigest.instanceFor(HashType.SHA_256)
        jdkHashCalculatorDigest.update(input)

        assertEquals(
            "532eaabd9574880dbf76b9b8cc00832c20a6ec113d682299550d7a6e0f345e25",
            jdkHashCalculatorDigest.result()
        )
    }

    @Test
    fun checkSHA384HashDigest() {
        val jdkHashCalculatorDigest = JdkHashGeneratorDigest.instanceFor(HashType.SHA_384)
        jdkHashCalculatorDigest.update(input)

        assertEquals(
            "7b8f4654076b80eb963911f19cfad1aaf4285ed48e826f6cde1b01a79aa73fadb5446e667fc4f90417782c91270540f3",
            jdkHashCalculatorDigest.result()
        )
    }

    @Test
    fun checkSHA512HashDigest() {
        val jdkHashCalculatorDigest = JdkHashGeneratorDigest.instanceFor(HashType.SHA_512)
        jdkHashCalculatorDigest.update(input)

        assertEquals(
            "c6ee9e33cf5c6715a1d148fd73f7318884b41adcb916021e2bc0e800a5c5dd97f5142178f6ae88c8fdd98e1afb0ce4c8d2c54b5f37b30b7da1997bb33b0b8a31",
            jdkHashCalculatorDigest.result()
        )
    }

    @Test
    fun checkSHA3224HashDigest() {
        val jdkHashCalculatorDigest = JdkHashGeneratorDigest.instanceFor(HashType.SHA3_224)
        jdkHashCalculatorDigest.update(input)

        assertEquals(
            "d40cc4f9630f21eef0b185bdd6a51eab1775c1cd6ae458066ecaf046",
            jdkHashCalculatorDigest.result()
        )
    }

    @Test
    fun checkSHA3256HashDigest() {
        val jdkHashCalculatorDigest = JdkHashGeneratorDigest.instanceFor(HashType.SHA3_256)
        jdkHashCalculatorDigest.update(input)

        assertEquals(
            "c0a5cca43b8aa79eb50e3464bc839dd6fd414fae0ddf928ca23dcebf8a8b8dd0",
            jdkHashCalculatorDigest.result()
        )
    }

    @Test
    fun checkSHA3384HashDigest() {
        val jdkHashCalculatorDigest = JdkHashGeneratorDigest.instanceFor(HashType.SHA3_384)
        jdkHashCalculatorDigest.update(input)

        assertEquals(
            "da73bfcba560692a019f52c37de4d5e3ab49ca39c6a75594e3c39d805388c4de9d0ff3927eb9e197536f5b0b3a515f0a",
            jdkHashCalculatorDigest.result()
        )
    }

    @Test
    fun checkSHA3512HashDigest() {
        val jdkHashCalculatorDigest = JdkHashGeneratorDigest.instanceFor(HashType.SHA3_512)
        jdkHashCalculatorDigest.update(input)

        assertEquals(
            "301bb421c971fbb7ed01dcc3a9976ce53df034022ba982b97d0f27d48c4f03883aabf7c6bc778aa7c383062f6823045a6d41b8a720afbb8a9607690f89fbe1a7",
            jdkHashCalculatorDigest.result()
        )
    }
}
