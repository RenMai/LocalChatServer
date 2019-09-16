import java.io.*
import java.net.*
import java.util.*

class ChatConnector(inputStream: InputStream, output: OutputStream, private val client: Socket) : ChatHistoryObserver, Runnable {
    private val scanner: Scanner = Scanner(inputStream)
    private val printOut: PrintStream = PrintStream(output)
    var username: String = ""
    var exit = false

    override fun newMessage(message: ChatMessage)
    {
        if (message.username != username)
        {
            printOut.println("${message.username} : ${message.message}")
        }
    }
    override fun run()
    {
        ChatHistory.registerObserver(this)
        printOut.println("Welcome to private chat Server!")
        var command = ""
        do {
            while (username == "")
            {
                printOut.println("Use the command -user [username] to set the username.")
                val command = scanner.nextLine()
                if (command.split(' ')[0] == "-user")
                {
                    val separateString = command.split(' ')
                    if (separateString.size > 1)
                    {
                        val inputCommand = command.substringAfter(" ")
                        if (Users.addUsername(inputCommand))
                        {
                            username = inputCommand
                            printOut.println("Your username is $username")
                            printOut.println("Now you can chat with your friends or use -help for help.")
                        }
                        else
                            printOut.println("This username is already taken!")
                    }
                    else
                        printOut.println("Oops, something went wrong!")
                }
            }
            command = scanner.nextLine()
            when (command.split(' ')[0])
            {

                "-users" -> printUserList(Users.getUserList())
                "-help" -> HelpList(printOut).show()
                "-exit" -> shutdown()
                "-history" -> printMessageHistory()

                else ->
                {
                    if (command.split(' ')[0][0].compareTo('-') == 0  )
                        {
                            HelpList(printOut).showError()
                        }
                    else
                    {
                        ChatHistory.insert(ChatMessage(username, command))
                        ChatHistory.notifyObservers(ChatMessage(username, command))
                    }
                }
            }
        } while (!exit)
    }

    private fun printMessageHistory() {
        for (message: ChatMessage in ChatHistory.returnMessageList()) {
            printOut.println(message.getMessageInOneLine())
        }
    }

    private fun printUserList(users: List<String>) {
        users.forEach { printOut.println(it) }
    }

    private fun shutdown() {
        ChatHistory.notifyObservers(ChatMessage(username, "has left the server"))
        client.close()
        Users.removeUsername(username)
        exit = true
    }
}