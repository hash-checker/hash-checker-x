package xyz.fartem.hashcheckerx.hash_generator.model

enum class HashType(val hashName: String) {
    MD5("MD5"),
    SHA_1("SHA-1"),
    SHA_224("SHA-224"),
    SHA_256("SHA-256"),
    SHA_384("SHA-384"),
    SHA_512("SHA-512"),
    SHA3_224("SHA3-224"),
    SHA3_256("SHA3-256"),
    SHA3_384("SHA3-384"),
    SHA3_512("SHA3-512"),
    CRC_32("CRC-32"),
    BLAKE2b("BLAKE2b"),
    FNV_1A_32("FNV1a32"),
    FNV_1A_64("FNV1a64"),
    FNV_1A_128("FNV1a128"),
    FNV_1A_256("FNV1a256"),
    FNV_1A_512("FNV1a512"),
    FNV_1A_1024("FNV1a1024");
}
