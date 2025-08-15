package xyz.fartem.hashcheckerx.history.api

import xyz.fartem.hashcheckerx.hash_generator.model.HashOutput

abstract class HashOutputDataStorage {
    abstract fun saveHashOutput(hashOutput: HashOutput): Boolean

    abstract fun deleteAllHashOutputs(): Boolean

    abstract fun getHashOutputs(): List<HashOutput>
}
