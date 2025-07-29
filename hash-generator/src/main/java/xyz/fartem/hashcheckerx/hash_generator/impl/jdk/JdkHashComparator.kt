package xyz.fartem.hashcheckerx.hash_generator.impl.jdk

import xyz.fartem.hashcheckerx.hash_generator.api.HashComparator
import javax.inject.Inject

class JdkHashComparator @Inject constructor() : HashComparator() {
    override fun compare(firstValue: String, secondValue: String): Boolean {
        return firstValue.lowercase() == secondValue.lowercase()
    }
}
