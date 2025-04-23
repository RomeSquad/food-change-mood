package presentation.action.suggest

import domain.use_case.suggest.SuggestEggFreeSweetUseCase
import presentation.MenuAction
import presentation.io.InputReader
import presentation.io.UiExecutor

class EggFreeSweetsAction(
    private val getSweetsWithNoEggsUseCase: SuggestEggFreeSweetUseCase
) : MenuAction {
    override val description: String = "Egg-Free Sweets"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        ui.displayResult("I will show you a random sweet without eggs, please wait...")

        val endLoopText = "exit"
        var isUserLikeSweet = "n"

        do {
            val randomSweet = getSweetsWithNoEggsUseCase.suggestRandomSweet()

            randomSweet.fold(
                onSuccess = { sweet ->
                    ui.displayResult(
                        "__________________________________________________________\n" +
                                "You get this random sweet:\n" +
                                "Meal name: ${sweet.name}\n" +
                                "Description: ${sweet.description}\n"
                    )

                    do {
                        ui.displayPrompt("Do you like this sweet? (y/n)\nYour choice: ")
                        isUserLikeSweet = inputReader.readString().trim()
                    } while (isUserLikeSweet != "y" && isUserLikeSweet != "n")

                    if (isUserLikeSweet == "y") {
                        ui.displayResult("Your final sweet is:\n$sweet")
                    }
                },
                onFailure = { exception ->
                    ui.displayError(exception.message ?: "Failed to fetch a sweet")
                    isUserLikeSweet = endLoopText
                }
            )
        } while (isUserLikeSweet == "n")
    }
}