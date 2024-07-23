import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)

    val archiveHolder = ArchiveHolder()
    val notesHolder = NotesHolder()
    val controller = Controller(archiveHolder, notesHolder, scanner)


        controller.showMainMenu()

    while (true) {
        val inputUser = Validator.getUserInput()
        controller.processInput(inputUser)
    }
}

const val CREATE = 0
const val BACK_PROGRAM = -1