package xyz.fartem.hashcheckerx.hash_generator.impl.jdk

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import xyz.fartem.hashcheckerx.hash_generator.api.HashGenerator
import xyz.fartem.hashcheckerx.hash_generator.model.HashType
import java.io.InputStream
import javax.inject.Inject

class JdkHashGenerator @Inject constructor(defaultHashType: HashType) : HashGenerator() {
    private var jdkHashCalculatorDigest = JdkHashGeneratorDigest.instanceFor(defaultHashType)

    override fun setHashType(hashType: HashType) {
        jdkHashCalculatorDigest = JdkHashGeneratorDigest.instanceFor(hashType)
    }

    override fun fromText(text: String): String {
        val bytes: ByteArray = text.toByteArray()
        jdkHashCalculatorDigest.update(bytes)

        return jdkHashCalculatorDigest.result()
    }

    override fun fromFile(context: Context, path: Uri): String {
        val fileStream: InputStream? = inputStreamFromUri(context, path)

        if (fileStream != null) {
            val buffer = ByteArray(1024)
            var read: Int

            do {
                read = fileStream.read(buffer)
                if (read > 0) {
                    jdkHashCalculatorDigest.update(buffer, read)
                }
            } while (read != -1)

            return jdkHashCalculatorDigest.result()
        }

        throw Exception("Can't generate hash from file")
    }

    private fun inputStreamFromUri(context: Context, path: Uri): InputStream? {
        return context.contentResolver.openInputStream(path)
    }

    override fun fromFolder(context: Context, path: Uri): String {
        val folderStream = inputStreamsFormFolder(context, path)

        for (stream in folderStream) {
            val buffer = ByteArray(1024)
            var read: Int

            do {
                read = stream.read(buffer)
                if (read > 0) {
                    jdkHashCalculatorDigest.update(buffer, read)
                }
            } while (read != -1)
        }

        return jdkHashCalculatorDigest.result()
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
