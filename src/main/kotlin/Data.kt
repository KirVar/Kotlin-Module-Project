import java.util.Scanner

object Data {
    var tableOfArchives: MutableMap<Int, Archive> = mutableMapOf()
    var currentArchiveNumber: Int? = null


    fun createArchive() {
        val heading = getTextIn(TypeOfUnit.ARCHIVE, PartOfUnit.HEADING)
        println("Архив '$heading'  добавлен в Хранилище ")
        tableOfArchives[tableOfArchives.size + 1] = Archive(heading)
        return
    }

    fun <T : StoresData> showMyDataUnits(typeOfUnit: TypeOfUnit, tableOfUnits: MutableMap<Int, T>) {
        println(
            if (typeOfUnit == TypeOfUnit.NOTE) "Архив '${tableOfArchives[currentArchiveNumber]!!.heading}'\nЗаметки:"
            else "Хранилище содержит следующие архивы:"
        )
        tableOfUnits.forEach { (key, value) ->
            println(" $key. '${value.heading}' ")
        }
    }

    fun createNote(currentArchiveNumber: Int) {
        val heading = getTextIn(TypeOfUnit.NOTE, PartOfUnit.HEADING)
        val content = getTextIn(TypeOfUnit.NOTE, PartOfUnit.CONTENT)
        tableOfArchives[currentArchiveNumber]!!.tableOfContents[
            tableOfArchives[currentArchiveNumber]!!.tableOfContents.size + 1
        ] = Note(heading, content)
        println("Заметка '$heading' добавлена в архив '${tableOfArchives[currentArchiveNumber]!!.heading}' ")
    }

    private fun getTextIn(typeOfUnit: TypeOfUnit, partOfUnit: PartOfUnit): String {
        var text: String
        do {
            println(
                "Введите ${
                    if (partOfUnit == PartOfUnit.HEADING) "название" else "текст"
                } ${
                    if (typeOfUnit == TypeOfUnit.NOTE) "заметки" else "архива"
                }"
            )
            text = Scanner(System.`in`).nextLine()
        } while (text == "")
        return text
    }

    fun readNote() {
        val numberOfNote = if (tableOfArchives[currentArchiveNumber]!!.tableOfContents.size == 1) 1
        else getNumberOfUnit(
            TypeOfUnit.NOTE,
            tableOfArchives[currentArchiveNumber]!!.tableOfContents
        )
        println("${tableOfArchives[currentArchiveNumber]!!.tableOfContents[numberOfNote]}")
        return
    }

    fun <T> getNumberOfUnit(
        typeOfUnit: TypeOfUnit,
        map: Map<Int, T>
    ): Int {
        inputLoop@ while (true) {
            println(
                "Введите ${
                    when (typeOfUnit) {
                        TypeOfUnit.NOTE -> "номер заметки"
                        TypeOfUnit.ARCHIVE -> "номер архива"
                        else -> "команду (одной цифрой)"
                    }
                }"
            )
            val temp: String = Scanner(System.`in`).nextLine()

            if (temp.isEmpty()) {
                println("Нет команды, повторите запрос")
                continue@inputLoop
            }
            var numberOfNotDigits = 0
            temp.toCharArray().forEach {
                if (!it.isDigit()) {
                    numberOfNotDigits++
                }
            }
            if (numberOfNotDigits > 0) {
                println("Введите цифры пожалуйста")
                continue@inputLoop
            }
            if (temp.toInt() !in map.keys) {
                println("Цифры - да, но не те. Нет ${
                        when (typeOfUnit) {
                            TypeOfUnit.NOTE -> "такой заметки."
                            TypeOfUnit.ARCHIVE -> "такого архива."
                            else -> "такой команды."
                        }
                    } Попробуем еще раз."
                )
                continue@inputLoop
            } else return temp.toInt()  //в итоге после всех проверок (во избежание null ошибок) получаем номер типа Int
        }
    }
}
interface StoresData {
    val heading: String
}

enum class TypeOfUnit {
    NOTE,
    ARCHIVE,
    COMMAND
}

enum class PartOfUnit {
    HEADING,
    CONTENT
}

data class Archive(
    override var heading: String
) : StoresData {
    var tableOfContents: MutableMap<Int, Note> = mutableMapOf()
}

data class Note(
    override val heading: String,
    val content: String
) : StoresData {
    override fun toString(): String {
        return "Название: '$heading' \nТекст: $content"
    }
}