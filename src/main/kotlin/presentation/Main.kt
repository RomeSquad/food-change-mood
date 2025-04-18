package presentation

import di.appModule
import di.useCaseModule
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() {
    startKoin {
        modules(appModule, useCaseModule)
    }
    val app: App = getKoin().get()
    app.start()
}