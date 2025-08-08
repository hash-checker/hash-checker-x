package xyz.fartem.hashcheckerx.hash_generator.impl

import android.content.Context
import android.net.Uri
import xyz.fartem.hashcheckerx.hash_generator.api.HashGenerator
import xyz.fartem.hashcheckerx.hash_generator.api.HashProvider
import xyz.fartem.hashcheckerx.hash_generator.model.HashType
import java.security.NoSuchAlgorithmException

class DefaultHashGenerator(private val hashProviders: List<HashProvider>) : HashGenerator() {

    override fun fromText(hashType: HashType, text: String): String {
        val hashProvider = findHashProviderOrThrowException(hashType)

        return hashProvider.fromText(hashType, text)
    }

    private fun findHashProviderOrThrowException(hashType: HashType): HashProvider {
        val hashProvider = hashProviders.firstOrNull { it.availableHashTypes().contains(hashType) }

        if (hashProvider == null) {
            throw NoSuchAlgorithmException("HashProvider for $hashType not found!")
        }

        return hashProvider
    }

    override fun fromFile(hashType: HashType, context: Context, path: Uri): String {
        val hashProvider = findHashProviderOrThrowException(hashType)

        return hashProvider.fromFile(hashType, context, path)
    }

    override fun fromFolder(hashType: HashType, context: Context, path: Uri): String {
        val hashProvider = findHashProviderOrThrowException(hashType)

        return hashProvider.fromFolder(hashType, context, path)
    }
}
