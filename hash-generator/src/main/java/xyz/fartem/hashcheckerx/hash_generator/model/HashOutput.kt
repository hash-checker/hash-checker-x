package xyz.fartem.hashcheckerx.hash_generator.model

import java.util.Date

data class HashOutput(
    val hashSource: HashSource,
    val hashType: HashType,
    val hashValue: String,
    val date: Date,
)
