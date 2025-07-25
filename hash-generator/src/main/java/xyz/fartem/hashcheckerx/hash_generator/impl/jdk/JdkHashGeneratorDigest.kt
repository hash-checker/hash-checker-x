package xyz.fartem.hashcheckerx.hash_generator.impl.jdk

import xyz.fartem.hashcheckerx.hash_generator.model.HashType
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException

class JdkHashGeneratorDigest private constructor() {
    private var messageDigest: MessageDigest? = null

    @Throws(NoSuchAlgorithmException::class, NoSuchProviderException::class)
    private fun setHashType(hashType: HashType, provider: String?) {
        messageDigest = if (provider == null) {
            MessageDigest.getInstance(hashType.hashName)
        } else {
            MessageDigest.getInstance(hashType.hashName, provider)
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
        @Throws(NoSuchAlgorithmException::class, NoSuchProviderException::class)
        fun instanceFor(hashType: HashType): JdkHashGeneratorDigest {
            val jdkHashCalculatorDigest = JdkHashGeneratorDigest()
            jdkHashCalculatorDigest.setHashType(hashType, null)

            return jdkHashCalculatorDigest
        }
    }
}
