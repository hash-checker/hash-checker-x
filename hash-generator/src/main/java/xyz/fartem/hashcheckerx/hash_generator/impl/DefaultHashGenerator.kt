package xyz.fartem.hashcheckerx.hash_generator.impl

import android.content.Context
import android.net.Uri
import xyz.fartem.hashcheckerx.hash_generator.api.HashGenerator
import xyz.fartem.hashcheckerx.hash_generator.api.HashProvider
import xyz.fartem.hashcheckerx.hash_generator.model.HashType
import java.security.NoSuchAlgorithmException

class DefaultHashGenerator(
    private val hashProviders: List<HashProvider>,
    defaultHashType: HashType,
) : HashGenerator() {
    private var hashType = defaultHashType

    override fun setHashType(hashType: HashType) {
        this.hashType = hashType
    }

    override fun fromText(text: String): String {
        val hashProvider = findHashProviderOrThrowException()

        return hashProvider.fromText(hashType, text)
    }

    private fun findHashProviderOrThrowException(): HashProvider {
        val hashProvider = hashProviders.firstOrNull { it.availableHashTypes().contains(hashType) }

        if (hashProvider == null) {
            throw NoSuchAlgorithmException("HashProvider for $hashType not found!")
        }

        return hashProvider
    }

    override fun fromFile(context: Context, path: Uri): String {
        val hashProvider = findHashProviderOrThrowException()

        return hashProvider.fromFile(hashType, context, path)
    }

    override fun fromFolder(context: Context, path: Uri): String {
        val hashProvider = findHashProviderOrThrowException()

        return hashProvider.fromFolder(hashType, context, path)
    }
}
