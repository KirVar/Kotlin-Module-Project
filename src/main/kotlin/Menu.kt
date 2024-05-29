import Data.createNote
import Data.currentArchiveNumber
import Data.getNumberOfUnit
import Data.readNote
import Data.showMyDataUnits
import Data.tableOfArchives

 val menuMain = mapOf(
    0 to "Создать архив",
    1 to "Открыть архив",
    2 to "Выйти из программы"
)
 val menuForArchives = mapOf(
    0 to "Создать заметку",
    1 to "Открыть заметку",
    2 to "Вернуться"
)
 val menuForNotes = mapOf(
    0 to "Прочитать заметку",
    1 to "Вернуться"
)

fun getCommand(menu: Map<Int, String>): Int {
    println("Возможные действия:")
    menu.forEach { (key, nameOfCommand) ->
        println("'$key' - $nameOfCommand")
    }
    return getNumberOfUnit(TypeOfUnit.COMMAND, menu)
}

fun goToArchiveMenu() {
    showMyDataUnits(TypeOfUnit.ARCHIVE, tableOfArchives)
    currentArchiveNumber = if (tableOfArchives.size == 1) 1
    else getNumberOfUnit(TypeOfUnit.ARCHIVE, tableOfArchives)
    while (true) {
        when (getCommand(menuForArchives)) {
            0 -> createNote(currentArchiveNumber!!)
            1 -> {
                if (tableOfArchives[currentArchiveNumber]!!.tableOfContents.isEmpty()) println("Этот архив пуст. Заметок в нем еще нет")
                else goToNotesMenu()
            }

            2 -> {
                currentArchiveNumber = null
                return
            }
        }
    }
}

fun goToNotesMenu() {
    showMyDataUnits(TypeOfUnit.NOTE, tableOfArchives[currentArchiveNumber]!!.tableOfContents)
    while (true) {
        when (getCommand(menuForNotes)) {
            0 -> readNote()
            1 -> return
        }
    }
}

