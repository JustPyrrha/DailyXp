package gay.pyrrha.dailyxp.core.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val xpDispatcher: XpDispatchers)

enum class XpDispatchers {
    Default,
    Io
}
