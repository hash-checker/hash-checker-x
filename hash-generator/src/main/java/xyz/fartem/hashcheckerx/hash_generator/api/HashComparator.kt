package xyz.fartem.hashcheckerx.hash_generator.api

abstract class HashComparator {
    abstract fun compare(firstValue: String, secondValue: String): Boolean
}