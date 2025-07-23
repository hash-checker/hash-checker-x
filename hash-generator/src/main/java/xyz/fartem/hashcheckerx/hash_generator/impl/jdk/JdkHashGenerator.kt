package xyz.fartem.hashcheckerx.hash_generator.impl.jdk

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import xyz.fartem.hashcheckerx.hash_generator.api.HashGenerator
import xyz.fartem.hashcheckerx.hash_generator.model.HashType
import java.io.IOException
import java.io.InputStream

class JdkHashGenerator : HashGenerator() {
    private var jdkHashCalculatorDigest: JdkHashGeneratorDigest? = null

    override fun setHashType(hashType: HashType) {
        jdkHashCalculatorDigest = JdkHashGeneratorDigest.instanceFor(hashType)
    }

    override fun fromText(text: String): String? {
        val bytes: ByteArray = text.toByteArray()
        jdkHashCalculatorDigest?.update(bytes)

        return jdkHashCalculatorDigest?.result()
    }

    override fun fromFile(context: Context, path: Uri): String? {
        try {
            val fileStream: InputStream? = inputStreamFromUri(context, path)

            if (fileStream != null) {
                return fromFile(fileStream)
            }
        } catch (e: Exception) {
            // TODO
//            LogUtils.e(e)
        }

        return null
    }

    @Throws(Exception::class)
    private fun inputStreamFromUri(context: Context, path: Uri): InputStream? {
        return context.contentResolver.openInputStream(path)
    }

    override fun fromFile(inputStream: InputStream): String? {
        try {
            val buffer = ByteArray(1024)
            var read: Int

            do {
                read = inputStream.read(buffer)
                if (read > 0) {
                    jdkHashCalculatorDigest?.update(buffer, read)
                }
            } while (read != -1)

            return jdkHashCalculatorDigest?.result()
        } catch (e: IOException) {
            // TODO
//                LogUtils.e(e)
        }

        return null
    }

    override fun fromFolder(context: Context, path: Uri): String? {
        try {
            val fileStream = inputStreamsFormFolder(context, path)

            return fromFolder(fileStream)
        } catch (e: java.lang.Exception) {
            // TODO
//            LogUtils.e(e)
        }

        return null
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

                try {
                    val inputStream = inputStreamFromUri(context, documentUri)
                    if (inputStream != null) {
                        inputStreams.add(inputStream)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            cursor.close()
        }

        return inputStreams
    }


    override fun fromFolder(inputStream: List<InputStream>): String? {
        for (stream in inputStream) {
            try {
                val buffer = ByteArray(1024)
                var read: Int

                do {
                    read = stream.read(buffer)
                    if (read > 0) {
                        jdkHashCalculatorDigest?.update(buffer, read)
                    }
                } while (read != -1)
            } catch (e: IOException) {
                // TODO
//                LogUtils.e(e)
            }
        }

        return jdkHashCalculatorDigest?.result()
    }
}