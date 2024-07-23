class NotesHolder : Holder() {

    override fun create(name: String, value: String, archIndex: Int) {
        storage.archives[archIndex].notesList.add(Note(name, value))
    }

    override fun getStorageSize(archIndex: Int): Int {
        return storage.archives[archIndex].notesList.size
    }
}