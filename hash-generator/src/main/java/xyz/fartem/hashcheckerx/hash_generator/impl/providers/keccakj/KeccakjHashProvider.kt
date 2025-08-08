package xyz.fartem.hashcheckerx.hash_generator.impl.providers.keccakj

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import com.github.aelstad.keccakj.provider.Constants
import xyz.fartem.hashcheckerx.hash_generator.api.HashProvider
import xyz.fartem.hashcheckerx.hash_generator.impl.DefaultHashUtils
import xyz.fartem.hashcheckerx.hash_generator.model.HashType
import java.io.InputStream
import java.security.MessageDigest

class KeccakjHashProvider : HashProvider() {
    override fun availableHashTypes(): Set<HashType> {
        return setOf(
            HashType.SHA3_224,
            HashType.SHA3_256,
            HashType.SHA3_384,
            HashType.SHA3_512,
        )
    }

    override fun fromText(hashType: HashType, text: String): String {
        val keccakjHashCalculatorDigest = KeccakjHashDigest.instanceFor(hashType)
        keccakjHashCalculatorDigest.update(text.toByteArray())

        return keccakjHashCalculatorDigest.result()
    }

    override fun fromFile(hashType: HashType, context: Context, path: Uri): String {
        val keccakjHashCalculatorDigest = KeccakjHashDigest.instanceFor(hashType)
        val fileStream: InputStream? = inputStreamFromUri(context, path)

        if (fileStream != null) {
            val buffer = ByteArray(1024)
            var read: Int

            do {
                read = fileStream.read(buffer)
                if (read > 0) {
                    keccakjHashCalculatorDigest.update(buffer, read)
                }
            } while (read != -1)

            return keccakjHashCalculatorDigest.result()
        }

        throw Exception("Can't generate hash from file")
    }

    private fun inputStreamFromUri(context: Context, path: Uri): InputStream? {
        return context.contentResolver.openInputStream(path)
    }

    override fun fromFolder(hashType: HashType, context: Context, path: Uri): String {
        val keccakjHashCalculatorDigest = KeccakjHashDigest.instanceFor(hashType)
        val folderStream = inputStreamsFormFolder(context, path)

        for (stream in folderStream) {
            val buffer = ByteArray(1024)
            var read: Int

            do {
                read = stream.read(buffer)
                if (read > 0) {
                    keccakjHashCalculatorDigest.update(buffer, read)
                }
            } while (read != -1)
        }

        return keccakjHashCalculatorDigest.result()
    }

    private fun inputStreamsFormFolder(context: Context, folderUri: Uri): List<InputStream> {
        val inputStreams: MutableList<InputStream> = ArrayList()

        val contentResolver = context.contentResolver
        val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
            folderUri,
            DocumentsContract.getTreeDocumentId(folderUri)
        )

        val cursor = contentResolver.query(
            childrenUri,
            arrayOf(DocumentsContract.Document.COLUMN_DOCUMENT_ID), null, null, null
        )

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val documentId = cursor.getString(0)
                val documentUri = DocumentsContract.buildDocumentUriUsingTree(folderUri, documentId)

                val inputStream = inputStreamFromUri(context, documentUri)
                if (inputStream != null) {
                    inputStreams.add(inputStream)
                }
            }

            cursor.close()
        }

        return inputStreams
    }
}

private class KeccakjHashDigest private constructor() {
    private var messageDigest: MessageDigest? = null

    private fun setHashType(hashType: HashType) {
        val algorithm = getHashNameByHashType(hashType)

        messageDigest = MessageDigest.getInstance(algorithm, Constants.PROVIDER)
    }

    private fun getHashNameByHashType(hashType: HashType): String {
        return when (hashType) {
            HashType.SHA3_224 -> Constants.SHA3_224
            HashType.SHA3_256 -> Constants.SHA3_256
            HashType.SHA3_384 -> Constants.SHA3_384
            HashType.SHA3_512 -> Constants.SHA3_512
            else -> ""
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
        return DefaultHashUtils.Companion.getStringFromByteArray(messageDigest!!.digest())
    }

    companion object {
        fun instanceFor(hashType: HashType): KeccakjHashDigest {
            val jdkHashCalculatorDigest = KeccakjHashDigest()
            jdkHashCalculatorDigest.setHashType(hashType)

            return jdkHashCalculatorDigest
        }
    }
}
