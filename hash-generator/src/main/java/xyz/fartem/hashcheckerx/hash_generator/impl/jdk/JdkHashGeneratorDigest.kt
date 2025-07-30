package xyz.fartem.hashcheckerx.hash_generator.impl.jdk

import xyz.fartem.hashcheckerx.hash_generator.model.HashType
import java.security.MessageDigest

class JdkHashGeneratorDigest private constructor() {
    private var messageDigest: MessageDigest? = null

    private fun setHashType(hashType: HashType) {
        messageDigest = MessageDigest.getInstance(getHashNameByHashType(hashType))
    }

    private fun getHashNameByHashType(hashType: HashType): String {
        return when (hashType) {
            HashType.MD5 -> "md5"
            HashType.SHA_1 -> "sha1"
            HashType.SHA_224 -> "sha224"
            HashType.SHA_256 -> "sha256"
            HashType.SHA_384 -> "sha384"
            HashType.SHA_512 -> "sha512"
            HashType.SHA3_224 -> "sha3_224"
            HashType.SHA3_256 -> "sha3_256"
            HashType.SHA3_384 -> "sha3_384"
            HashType.SHA3_512 -> "sha3_512"
        }
    }

    fun update(input: ByteArray?) {
        messageDigest!!.reset()

        if (input != null) {
            messageDigest!!.update(input)
        }
    }

    fun update(input: ByteArray?, length: Int) {
        if (input != null) {
            messageDigest!!.update(input, 0, length)
        }
    }

    fun result(): String {
        return JdkHashUtils.getStringFromByteArray(messageDigest!!.digest())
    }

    companion object {
        fun instanceFor(hashType: HashType): JdkHashGeneratorDigest {
            val jdkHashCalculatorDigest = JdkHashGeneratorDigest()
            jdkHashCalculatorDigest.setHashType(hashType)

            return jdkHashCalculatorDigest
        }
    }
}
