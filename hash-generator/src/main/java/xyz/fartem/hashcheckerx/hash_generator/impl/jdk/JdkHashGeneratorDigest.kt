package xyz.fartem.hashcheckerx.hash_generator.impl.jdk

import xyz.fartem.hashcheckerx.hash_generator.model.HashType
import java.security.MessageDigest

class JdkHashGeneratorDigest private constructor() {
    private var messageDigest: MessageDigest? = null

    private fun setHashType(hashType: HashType) {
        messageDigest = MessageDigest.getInstance(hashType.hashName)
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
