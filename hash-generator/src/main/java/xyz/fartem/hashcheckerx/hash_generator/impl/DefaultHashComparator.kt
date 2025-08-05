package xyz.fartem.hashcheckerx.hash_generator.impl

import xyz.fartem.hashcheckerx.hash_generator.api.HashComparator

class DefaultHashComparator : HashComparator() {
    override fun compare(firstValue: String, secondValue: String): Boolean {
        return firstValue.lowercase() == secondValue.lowercase()
    }
}
