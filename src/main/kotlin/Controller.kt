import java.util.Scanner
import kotlin.system.exitProcess

class Controller(
    private val archHolder: ArchiveHolder,
    private val notesHolder: NotesHolder,
    private val scanner: Scanner,
) {
     private var currentBreakpoint: BreakPoints = BreakPoints.MainMenu


    private fun navigateBack() {
        when (currentBreakpoint) {
            is BreakPoints.MainMenu -> exitProcess(0)
            else -> Unit
        }
    }

    fun showMainMenu() {
        println(ARCHIVE_LIST)
        println(CREATE_ARCHIVE)
        archHolder.open()
    }

    private fun createArchive(name:String) {
        archHolder.create(name)
    }

    private fun openArchive(archIndex: Int) {
        BreakPoints.archIndex = archIndex - 1
        archHolder.open(BreakPoints.archIndex)
    }

    private fun createNoteInArchive(name: String, value: String) {
        notesHolder.create(
            name = name,
            value = value,
            archIndex = BreakPoints.archIndex)
    }

    private fun openNote(notesIndex: Int) {
        BreakPoints.noteIndex = notesIndex
        notesHolder.open(BreakPoints.archIndex, notesIndex - 1)
    }

    private fun getNoteCount(): Int {
        return notesHolder.getStorageSize(BreakPoints.archIndex)
    }

    private fun getArchiveCount(): Int {
        return when (currentBreakpoint) {
            is BreakPoints.ArchiveHolder -> archHolder.getStorageSize(BreakPoints.archIndex)
            BreakPoints.MainMenu -> archHolder.getStorageSize()
            else -> 0
        }
    }

    fun processInput(userInput: Int) {
        val size = getCurrentListSize()
        when (userInput) {
            CREATE -> {
                verifyPosition()
                displayCurrentState()
            }
            BACK_PROGRAM -> {
                navigateBack()
                decreaseBreakpoint()
                displayCurrentState()
            }
            in (1..size) -> {
                increaseBreakpoint()
                validatePositionAndDisplay()
            }
            else -> {
                println(ERROR_INVALID_OPTION)
                displayCurrentState()
            }
        }
    }

    private fun displayCurrentState() {
        when (currentBreakpoint) {
            BreakPoints.MainMenu -> showMainMenu()
            BreakPoints.ArchiveHolder -> openArchive(BreakPoints.archIndex + 1)
            BreakPoints.NotesHolder -> openNote(BreakPoints.noteIndex)
            BreakPoints.Note -> Unit
        }
    }

    private fun decreaseBreakpoint() {
        currentBreakpoint = when (currentBreakpoint) {
            BreakPoints.MainMenu -> BreakPoints.MainMenu
            BreakPoints.ArchiveHolder -> BreakPoints.MainMenu
            BreakPoints.NotesHolder -> BreakPoints.ArchiveHolder
            BreakPoints.Note -> BreakPoints.ArchiveHolder
        }
    }

    private fun increaseBreakpoint() {
        currentBreakpoint = when(currentBreakpoint) {
            BreakPoints.MainMenu -> BreakPoints.ArchiveHolder
            BreakPoints.ArchiveHolder -> BreakPoints.NotesHolder
            BreakPoints.NotesHolder -> BreakPoints.Note
            BreakPoints.Note -> BreakPoints.Note
        }
    }

    private fun getCurrentListSize(): Int {
        return when (currentBreakpoint) {
            BreakPoints.MainMenu -> getArchiveCount()

            BreakPoints.ArchiveHolder -> getNoteCount()

            BreakPoints.NotesHolder -> getNoteCount()

            BreakPoints.Note -> getNoteCount()

        }
    }

    private fun verifyPosition() {
        when (currentBreakpoint) {
            BreakPoints.MainMenu -> {
                println(ARCHIVE_NAME)
                val name = Validator.validateNonEmptyInput(scanner.nextLine())
                createArchive(name)
            }
            BreakPoints.ArchiveHolder -> {
                println(NOTES_NAME)
                val name =  Validator.validateNonEmptyInput(scanner.nextLine())
                println(NOTE_TEXT)
                val text = Validator.validateNonEmptyInput(scanner.nextLine())
                createNoteInArchive(name, text)
            }
            else -> error(WRONG_BREAKPOINT)
        }
    }

    private fun validatePositionAndDisplay() {
        when (currentBreakpoint) {
            BreakPoints.MainMenu -> {
                showMainMenu()
            }
            BreakPoints.ArchiveHolder -> {
                archHolder.open(BreakPoints.archIndex)
            }
            BreakPoints.NotesHolder -> {
                notesHolder.open(BreakPoints.archIndex, BreakPoints.noteIndex)
            }
            else -> error(WRONG_BREAKPOINT)
        }
    }

    private sealed class BreakPoints {
        companion object {
            var archIndex: Int = 0
            var noteIndex: Int = 0
        }
         object MainMenu : BreakPoints()
         object ArchiveHolder : BreakPoints()
         object NotesHolder : BreakPoints()
         object Note: BreakPoints()
    }

    companion object {
        private const val ARCHIVE_NAME = "Введите название архива:"
        private const val NOTES_NAME = "Введите название заметки"
        private const val NOTE_TEXT = "Введите текст заметки"
        private const val ARCHIVE_LIST = "\nСписок архивов:"
        private const val CREATE_ARCHIVE = "0. Создать архив"
        private const val ERROR_INVALID_OPTION = "Нет такого варианта"
        private const val WRONG_BREAKPOINT = "Wrong breakpoint"
    }
}