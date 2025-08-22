package xyz.fartem.hashcheckerx.history.impl

import xyz.fartem.hashcheckerx.hash_generator.model.HashOutput
import xyz.fartem.hashcheckerx.history.api.HashOutputDataStorage

class LimitedLocalHashOutputDataStorage : HashOutputDataStorage() {
    private val storage = mutableListOf<HashOutput>()
    private val limit = 10

    override fun saveHashOutput(hashOutput: HashOutput): Boolean {
        if (storage.size == limit) {
            storage.removeAt(0)
        }

        storage.add(hashOutput)

        return true
    }

    override fun deleteAllHashOutputs(): Boolean {
        if (storage.isNotEmpty()) {
            storage.clear()

            return true
        }

        return false
    }

    override fun getHashOutputs(): List<HashOutput> {
        return storage
    }
}
