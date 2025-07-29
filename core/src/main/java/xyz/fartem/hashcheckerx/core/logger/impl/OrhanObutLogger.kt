package xyz.fartem.hashcheckerx.core.logger.impl

import xyz.fartem.hashcheckerx.core.logger.api.Logger
import javax.inject.Inject

class OrhanObutLogger @Inject constructor() : Logger() {
    override fun debug(message: String) {
        com.orhanobut.logger.Logger.d(message)
    }

    override fun error(message: String) {
        com.orhanobut.logger.Logger.e(message)
    }
}
