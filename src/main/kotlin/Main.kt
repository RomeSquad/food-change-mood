import di.appModule
import di.uiModule
import di.useCaseModule
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import presentation.App

fun main() {
    startKoin {
        modules(appModule, useCaseModule, uiModule)
    }

    val app: App = getKoin().get()
    app.start()
}