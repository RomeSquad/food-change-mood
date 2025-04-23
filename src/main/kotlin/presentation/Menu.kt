package presentation

class Menu(private val actions: List<MenuAction>) {
    fun getAction(index: Int?): MenuAction? {
        if(index == null) return null
        return actions.getOrNull(index-1)
    }

    fun getActions(): List<MenuAction> = actions
}