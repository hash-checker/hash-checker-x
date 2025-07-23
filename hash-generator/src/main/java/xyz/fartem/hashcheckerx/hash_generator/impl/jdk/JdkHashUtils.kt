package xyz.fartem.hashcheckerx.hash_generator.impl.jdk

class JdkHashUtils private constructor() {

    companion object {
        private val HEX_DIGITS = "0123456789ABCDEF".toCharArray()

        fun getStringFromByteArray(data: ByteArray): String {
            val chars = CharArray(data.size * 2)
            for (i in data.indices) {
                chars[i * 2] = HEX_DIGITS[(data[i].toInt() ushr 4) and 0xf]
                chars[i * 2 + 1] = HEX_DIGITS[data[i].toInt() and 0xf]
            }

            return String(chars).lowercase()
        }

        fun getStringFromLong(data: Long): String {
            return String.format("%08x", data)
        }
    }
}