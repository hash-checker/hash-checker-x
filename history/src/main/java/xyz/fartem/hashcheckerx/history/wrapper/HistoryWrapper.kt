package xyz.fartem.hashcheckerx.history.wrapper

import xyz.fartem.hashcheckerx.hash_generator.model.HashOutput
import xyz.fartem.hashcheckerx.history.api.HashOutputDataStorage

class HistoryWrapper(private val dataStorage: HashOutputDataStorage) {
    fun saveHashOutput(hashOutput: HashOutput): Boolean {
        return dataStorage.saveHashOutput(hashOutput)
    }

    fun deleteAllHashOutputs(): Boolean {
        return dataStorage.deleteAllHashOutputs()
    }

    fun getHashOutputs(): List<HashOutput> {
        return dataStorage.getHashOutputs()
    }
}
