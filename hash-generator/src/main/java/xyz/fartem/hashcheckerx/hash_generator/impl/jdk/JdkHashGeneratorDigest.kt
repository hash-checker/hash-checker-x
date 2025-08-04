package xyz.fartem.hashcheckerx.hash_generator.impl.jdk

import com.github.aelstad.keccakj.provider.Constants
import xyz.fartem.hashcheckerx.hash_generator.model.HashType
import java.security.MessageDigest

class JdkHashGeneratorDigest private constructor() {
    private var messageDigest: MessageDigest? = null

    private fun setHashType(hashType: HashType) {
        val algorithm = getHashNameByHashType(hashType)
        val provider = getHashProviderByHashType(hashType)

        messageDigest = if (provider == null) {
            MessageDigest.getInstance(algorithm)
        } else {
            MessageDigest.getInstance(algorithm, provider)
        }
    }

    private fun getHashNameByHashType(hashType: HashType): String {
        return when (hashType) {
            HashType.MD5 -> "MD5"
            HashType.SHA_1 -> "SHA1"
            HashType.SHA_224 -> "SHA224"
            HashType.SHA_256 -> "SHA256"
            HashType.SHA_384 -> "SHA384"
            HashType.SHA_512 -> "SHA512"
            HashType.SHA3_224 -> "SHA3-224"
            HashType.SHA3_256 -> "SHA3-256"
            HashType.SHA3_384 -> "SHA3-384"
            HashType.SHA3_512 -> "SHA3-512"
        }
    }

    private fun getHashProviderByHashType(hashType: HashType): String? {
        return when (hashType) {
            HashType.MD5,
            HashType.SHA_1,
            HashType.SHA_224,
            HashType.SHA_256,
            HashType.SHA_384,
            HashType.SHA_512 -> null

            HashType.SHA3_224,
            HashType.SHA3_256,
            HashType.SHA3_384,
            HashType.SHA3_512 -> Constants.PROVIDER
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
