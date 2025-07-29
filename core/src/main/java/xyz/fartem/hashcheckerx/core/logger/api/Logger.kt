package xyz.fartem.hashcheckerx.core.logger.api

abstract class Logger {
    abstract fun debug(message: String)

    abstract fun error(message: String)
}
