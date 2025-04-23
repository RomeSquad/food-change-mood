package presentation

import presentation.io.InputReader
import presentation.io.UiExecutor

interface MenuAction {
    val description: String

    fun execute (
        ui: UiExecutor,
        inputReader: InputReader
    )
}