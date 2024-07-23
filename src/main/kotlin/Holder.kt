

abstract class Holder {

    protected val storage = Storage


    open fun open(archIndex: Int? = null, notesIndex: Int? = null) {
        when {
            archIndex == null -> showMainMenu()
            notesIndex == null -> showNotesList(archIndex)
            else -> showNoteDetails(archIndex, notesIndex)
        }
    }

    private fun showNoteDetails(archIndex: Int, notesIndex: Int) {
        println(storage.archives[archIndex].notesList[notesIndex].name)
        println("Текст заметки: ${storage.archives[archIndex].notesList[notesIndex].value}")
        println("\n Enter for Quit")
    }

    private fun showNotesList(archIndex: Int) {
        var count = 0
        storage.archives[archIndex].notesList.forEach { elem ->
            println("${++count}. $elem")
        }
        println(ENTER_FOR_QUIT)
    }

    private fun showMainMenu() {
        var count = 0
        storage.archives.forEach { elem ->
            println("${++count}. ${elem.name}")
        }
        println(ENTER_FOR_QUIT)
    }

    abstract fun getStorageSize(archIndex: Int = -1): Int

    abstract fun create(name: String, value: String = STILL_NOTHING, archIndex: Int = 0)

    companion object {
        private const val ENTER_FOR_QUIT = "Enter for Quit"
        private const val STILL_NOTHING = "Still nothing"
    }
}



