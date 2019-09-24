import java.io.PrintStream

class HelpList(private val printOut: PrintStream) {
    //some command information
    fun show() {
        printOut.println("Input -exit for exit")
        printOut.println("Input -users to see all users in this chat")
        printOut.println("Input -history to see all chat history")
    }

    fun showError() {
        printOut.println("Wrong command")
    }
}