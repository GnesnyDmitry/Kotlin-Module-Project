private const val WRONG_INDEX = "Wrong index"

class ArchiveHolder : Holder() {

    override fun open(archIndex: Int?, notesIndex: Int?) {
        if(archIndex != null) {
            if (archIndex > storage.archives.size - 1) {
                println(WRONG_INDEX)
            } else {
                println("\nСписок заметок:\n0. Создать заметку")
            }
        }

        super.open(archIndex, notesIndex)
    }

    override fun getStorageSize(archIndex: Int): Int {
        return storage.archives.size
    }

    override fun create(name: String, value: String, archIndex: Int) {
        val listNotes: MutableList<Note> = mutableListOf()
        storage.archives.add(Archive(name, listNotes))
      }

}

