package xyz.fartem.hashcheckerx.hash_generator.api

import android.content.Context
import android.net.Uri
import xyz.fartem.hashcheckerx.hash_generator.model.HashType

abstract class HashGenerator {
    abstract fun setHashType(hashType: HashType)

    abstract fun fromText(text: String): String

    abstract fun fromFile(context: Context, path: Uri): String

    abstract fun fromFolder(context: Context, path: Uri): String
}
