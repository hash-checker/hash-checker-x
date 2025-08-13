package xyz.fartem.hashcheckerx.hash_generator.impl.providers.custom.fnv1a

import android.content.Context
import android.net.Uri
import xyz.fartem.hashcheckerx.hash_generator.api.HashProvider
import xyz.fartem.hashcheckerx.hash_generator.model.HashType
import java.io.InputStream

class CustomFNV1aHashProvider : HashProvider() {
    override fun availableHashTypes(): Set<HashType> {
        return setOf(
            HashType.FNV_1A_32,
            HashType.FNV_1A_64,
            HashType.FNV_1A_128,
            HashType.FNV_1A_256,
            HashType.FNV_1A_512,
            HashType.FNV_1A_1024,
        )
    }

    override fun fromText(hashType: HashType, text: String): String {
        val customFNV1aHashDigest = CustomFNV1aHashDigest.instanceFor(hashType)
        customFNV1aHashDigest.update(text.toByteArray())

        return customFNV1aHashDigest.result()
    }

    override fun fromFile(hashType: HashType, context: Context, path: Uri): String {
        val customFNV1aHashDigest = CustomFNV1aHashDigest.instanceFor(hashType)
        val fileStream: InputStream? = inputStreamFromUri(context, path)

        if (fileStream != null) {
            val buffer = ByteArray(1024)
            var read: Int

            do {
                read = fileStream.read(buffer)
                if (read > 0) {
                    customFNV1aHashDigest.update(buffer, read)
                }
            } while (read != -1)

            return customFNV1aHashDigest.result()
        }

        throw Exception("Can't generate hash from file")
    }

    override fun fromFolder(hashType: HashType, context: Context, path: Uri): String {
        val customFNV1aHashDigest = CustomFNV1aHashDigest.instanceFor(hashType)
        val folderStream = inputStreamsFormFolder(context, path)

        for (stream in folderStream) {
            val buffer = ByteArray(1024)
            var read: Int

            do {
                read = stream.read(buffer)
                if (read > 0) {
                    customFNV1aHashDigest.update(buffer, read)
                }
            } while (read != -1)
        }

        return customFNV1aHashDigest.result()
    }
}

private class CustomFNV1aHashDigest private constructor(private val fnV1a: FNV1a){
    fun update(input: ByteArray?) {
        fnV1a.reset()

        if (input != null) {
            fnV1a.update(input)
        }
    }

    fun update(input: ByteArray?, length: Int) {
        if (input != null) {
            fnV1a.update(input, 0, length)
        }
    }

    fun result(): String {
        return fnV1a.value
    }

    companion object {
        fun instanceFor(hashType: HashType): CustomFNV1aHashDigest {
            val fnV1a = FNV1a()
            fnV1a.setInstance(hashType.hashName)

            return CustomFNV1aHashDigest(fnV1a)
        }
    }
}
