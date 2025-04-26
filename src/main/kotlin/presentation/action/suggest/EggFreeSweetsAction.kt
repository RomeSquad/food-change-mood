package presentation.action.suggest

import data.model.Meal
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

                    displaySweetDetails(ui, randomSweet)

                    isUserLikeSweet = promptUserForLike(inputReader, ui)

                    if (isUserLikeSweet == "y") {
                        ui.displayResult("Your final sweet is:\n$randomSweet")
                    }


        } while (isUserLikeSweet == "n")
    }

    private fun displaySweetDetails(ui: UiExecutor, sweet: Meal) {
        ui.displayResult(
            "__________________________________________________________\n" +
                    "You get this random sweet:\n" +
                    "Meal name: ${sweet.name}\n" +
                    "Description: ${sweet.description}\n"
        )
    }

    private fun promptUserForLike(inputReader: InputReader, ui: UiExecutor): String {
        var userChoice: String
        do {
            ui.displayPrompt("Do you like this sweet? (y/n)\nYour choice: ")
            userChoice = inputReader.readString().trim()
        } while (userChoice != "y" && userChoice != "n")
        return userChoice
    }

}