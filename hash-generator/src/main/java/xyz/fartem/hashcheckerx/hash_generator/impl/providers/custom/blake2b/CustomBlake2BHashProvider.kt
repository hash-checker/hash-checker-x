package xyz.fartem.hashcheckerx.hash_generator.impl.providers.custom.blake2b

import android.content.Context
import android.net.Uri
import xyz.fartem.hashcheckerx.hash_generator.api.HashProvider
import xyz.fartem.hashcheckerx.hash_generator.model.HashType
import java.io.InputStream

class CustomBlake2BHashProvider : HashProvider() {
    override fun availableHashTypes(): Set<HashType> {
        return setOf(HashType.BLAKE2b)
    }

    override fun fromText(hashType: HashType, text: String): String {
        val customBlake2BHashDigest = CustomBlake2BHashDigest()
        customBlake2BHashDigest.update(text.toByteArray())

        return customBlake2BHashDigest.result()
    }

    override fun fromFile(hashType: HashType, context: Context, path: Uri): String {
        val blake2BHashDigest = CustomBlake2BHashDigest()
        val fileStream: InputStream? = inputStreamFromUri(context, path)

        if (fileStream != null) {
            val buffer = ByteArray(1024)
            var read: Int

            do {
                read = fileStream.read(buffer)
                if (read > 0) {
                    blake2BHashDigest.update(buffer, read)
                }
            } while (read != -1)

            return blake2BHashDigest.result()
        }

        throw Exception("Can't generate hash from file")
    }

    override fun fromFolder(hashType: HashType, context: Context, path: Uri): String {
        val blake2BHashDigest = CustomBlake2BHashDigest()
        val folderStream = inputStreamsFormFolder(context, path)

        for (stream in folderStream) {
            val buffer = ByteArray(1024)
            var read: Int

            do {
                read = stream.read(buffer)
                if (read > 0) {
                    blake2BHashDigest.update(buffer, read)
                }
            } while (read != -1)
        }

        return blake2BHashDigest.result()
    }
}

private class CustomBlake2BHashDigest {
    private val blake2B = Blake2B()

    fun update(input: ByteArray?) {
        blake2B.reset()

        if (input != null) {
            blake2B.update(input)
        }
    }

    fun update(input: ByteArray?, length: Int) {
        if (input != null) {
            blake2B.update(input, 0, length)
        }
    }

    fun result(): String {
        return blake2B.value
    }
}
