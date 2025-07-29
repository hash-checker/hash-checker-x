package xyz.fartem.hashcheckerx.core.logger.impl

import xyz.fartem.hashcheckerx.core.logger.api.Logger

class OrhanObutLoggerImpl : Logger() {
    override fun debug(message: String) {
        com.orhanobut.logger.Logger.d(message)
    }

    override fun error(message: String) {
        com.orhanobut.logger.Logger.e(message)
    }
}
