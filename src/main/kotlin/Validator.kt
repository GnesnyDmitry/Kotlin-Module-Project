import java.util.Scanner

private const val IS_NOT_EMPTY = "Не может быть пустым"

private const val ONLY_INT = "Ошибка. Пожалуйста, введите целое число:"

object Validator {

    private val scanner = Scanner(System.`in`)

    fun validateNonEmptyInput(name: String): String {
        var nameTmp = name
        while(nameTmp.isEmpty()) {
                println(IS_NOT_EMPTY)
                nameTmp = scanner.nextLine()
        }
        return nameTmp
    }

    fun getUserInput(): Int {
        while (true) {
            val input = scanner.nextLine()
            if (input.isEmpty()) {
                return BACK_PROGRAM
            } else if (isInt(input) && input.toInt() >= 0) {
                return input.toInt()
            } else {
                println(ONLY_INT)
            }
        }
    }

    private fun isInt(input: String): Boolean {
        return input.toIntOrNull() != null
    }
}
