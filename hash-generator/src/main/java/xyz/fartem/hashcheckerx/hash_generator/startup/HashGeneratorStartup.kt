package xyz.fartem.hashcheckerx.hash_generator.startup

import com.github.aelstad.keccakj.provider.KeccakjProvider
import java.security.Security

object HashGeneratorStartup {
    fun start() {
        Security.addProvider(KeccakjProvider())
    }
}
