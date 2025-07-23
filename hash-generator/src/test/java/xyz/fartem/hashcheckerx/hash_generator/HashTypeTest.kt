package xyz.fartem.hashcheckerx.hash_generator

import org.junit.Assert.assertEquals
import org.junit.Test
import xyz.fartem.hashcheckerx.hash_generator.model.HashType

class HashTypeTest {
    @Test
    fun checkMD5HashName() {
        assertEquals(
            HashType.MD5.hashName,
            "MD5"
        )
    }

    @Test
    fun checkSHA1HashName() {
        assertEquals(
            HashType.SHA_1.hashName,
            "SHA-1"
        )
    }

    @Test
    fun checkSHA224HashName() {
        assertEquals(
            HashType.SHA_224.hashName,
            "SHA-224"
        )
    }

    @Test
    fun checkSHA256HashName() {
        assertEquals(
            HashType.SHA_256.hashName,
            "SHA-256"
        )
    }

    @Test
    fun checkSHA384HashName() {
        assertEquals(
            HashType.SHA_384.hashName,
            "SHA-384"
        )
    }

    @Test
    fun checkSHA512HashName() {
        assertEquals(
            HashType.SHA_512.hashName,
            "SHA-512"
        )
    }

    @Test
    fun checkSHA3224HashName() {
        assertEquals(
            HashType.SHA3_224.hashName,
            "SHA3-224"
        )
    }

    @Test
    fun checkSHA3256HashName() {
        assertEquals(
            HashType.SHA3_256.hashName,
            "SHA3-256"
        )
    }

    @Test
    fun checkSHA3384HashName() {
        assertEquals(
            HashType.SHA3_384.hashName,
            "SHA3-384"
        )
    }

    @Test
    fun checkSHA3512HashName() {
        assertEquals(
            HashType.SHA3_512.hashName,
            "SHA3-512"
        )
    }
}
