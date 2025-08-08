package xyz.fartem.hashcheckerx.hash_generator.impl.providers.crc32

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import xyz.fartem.hashcheckerx.hash_generator.api.HashProvider
import xyz.fartem.hashcheckerx.hash_generator.model.HashType
import java.io.InputStream
import java.util.zip.CRC32


class Crc32HashProvider : HashProvider() {
    override fun availableHashTypes(): Set<HashType> {
        return setOf(HashType.CRC_32)
    }

    override fun fromText(hashType: HashType, text: String): String {
        val crc32HashCalculatorDigest = Crc32HashCalculatorDigest()
        crc32HashCalculatorDigest.update(text.toByteArray())

        return crc32HashCalculatorDigest.result()
    }

    override fun fromFile(hashType: HashType, context: Context, path: Uri): String {
        val crc32HashCalculatorDigest = Crc32HashCalculatorDigest()
        val fileStream: InputStream? = inputStreamFromUri(context, path)

        if (fileStream != null) {
            val buffer = ByteArray(1024)
            var read: Int

            do {
                read = fileStream.read(buffer)
                if (read > 0) {
                    crc32HashCalculatorDigest.update(buffer, read)
                }
            } while (read != -1)

            return crc32HashCalculatorDigest.result()
        }

        throw Exception("Can't generate hash from file")
    }

    private fun inputStreamFromUri(context: Context, path: Uri): InputStream? {
        return context.contentResolver.openInputStream(path)
    }

    override fun fromFolder(hashType: HashType, context: Context, path: Uri): String {
        val crc32HashCalculatorDigest = Crc32HashCalculatorDigest()
        val folderStream = inputStreamsFormFolder(context, path)

        for (stream in folderStream) {
            val buffer = ByteArray(1024)
            var read: Int

            do {
                read = stream.read(buffer)
                if (read > 0) {
                    crc32HashCalculatorDigest.update(buffer, read)
                }
            } while (read != -1)
        }

        return crc32HashCalculatorDigest.result()
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

private class Crc32HashCalculatorDigest {
    private var crc32 = CRC32()

    fun update(input: ByteArray) {
        crc32.reset()
        crc32.update(input)
    }

    fun update(input: ByteArray, length: Int) {
        crc32.update(input, 0, length)
    }

    fun result(): String {
        return String.format("%08x", crc32.value)
    }
}
