import Data.createArchive
import Data.tableOfArchives

fun main() {
        println("Вас приветствует консольное приложение Заметки")
        while (true) {
            when (getCommand(menuMain)) {
                0 -> createArchive()
                1 -> {
                    if (tableOfArchives.isEmpty()) println("Список архивов пуст")
                    else goToArchiveMenu()
                }

                2 -> {
                    println("Будем ждать вас еще, хорошего дня")
                    break
                }
            }
        }
    }
