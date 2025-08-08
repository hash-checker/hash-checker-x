package xyz.fartem.hashcheckerx.hash_generator.api

import android.content.Context
import android.net.Uri
import xyz.fartem.hashcheckerx.hash_generator.model.HashType

abstract class HashGenerator {
    abstract fun fromText(hashType: HashType, text: String): String

    abstract fun fromFile(hashType: HashType, context: Context, path: Uri): String

    abstract fun fromFolder(hashType: HashType, context: Context, path: Uri): String
}
