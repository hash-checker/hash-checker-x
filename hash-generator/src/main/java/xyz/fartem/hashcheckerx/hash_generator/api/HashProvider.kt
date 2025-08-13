package xyz.fartem.hashcheckerx.hash_generator.api

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import xyz.fartem.hashcheckerx.hash_generator.model.HashType
import java.io.InputStream

abstract class HashProvider {
    abstract fun availableHashTypes(): Set<HashType>

    abstract fun fromText(hashType: HashType, text: String): String

    abstract fun fromFile(hashType: HashType, context: Context, path: Uri): String

    fun inputStreamFromUri(context: Context, path: Uri): InputStream? {
        return context.contentResolver.openInputStream(path)
    }

    abstract fun fromFolder(hashType: HashType, context: Context, path: Uri): String

    fun inputStreamsFormFolder(context: Context, folderUri: Uri): List<InputStream> {
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
