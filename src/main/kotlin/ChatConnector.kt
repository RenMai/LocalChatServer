import java.io.*
import java.net.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class ChatConnector(inputStream: InputStream, output: OutputStream, private val client: Socket) : ChatHistoryObserver,
    Runnable {
    private val scanner: Scanner = Scanner(inputStream)
    private val printOut: PrintStream = PrintStream(output)
    var username: String = ""
    var exit = false
    private val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
    override fun newMessage(message: ChatMessage) {
        if (message.username != username) {
            printOut.println("${message.chatTime} ${message.username}: ${message.message}")
        }
    } // to print in terminal

    override fun run() {
        createUsername()
        chatting()
    }

    private fun createUsername() {
        ChatHistory.registerObserver(this)
        printOut.println("Welcome to private chat Server!")
        while (username == "") {
            printOut.println("Use the command -user [username] to set the username.\r\nUse the command -exit to quit.")
            val command = scanner.nextLine()
            if (command.split(' ')[0] == "-user") {
                //check the command
                val separateString = command.split(' ')
                if (separateString.size > 1) {
                    val inputCommand = command.substringAfter(" ")  //String after <blank> will become the username
                    if (Users.addUsername(inputCommand)) {
                        username = inputCommand
                        printOut.println("Your username is $username")
                        printOut.println("Now you can chat with your friends or use -help for help.")
                    } else
                        printOut.println("This username is already taken!")
                } else
                    printOut.println("Oops, something went wrong!")
            } else if (command == "-exit") {
                quickShutdown()
            }
        }
    }

    private fun chatting() {
        TopChatter.newMessage(ChatMessage(username, "", ""))    //add user to top chatters for counting
        printChatters(TopChatter.getTopChatterList())
        var command = ""
        do {
            command = scanner.nextLine()
            when (command.split(' ')[0]) {

                "-users" -> printUserList(Users.getUserList())
                "-help" -> HelpList(printOut).show()
                "-exit" -> shutdown()
                "-history" -> printMessageHistory()
                //check if user want to do some command
                else -> {
                    if (command.split(' ')[0][0].compareTo('-') == 0) {
                        HelpList(printOut).showError()
                    } else {
                        val current = LocalDateTime.now()
                        val chatTime = current.format(formatter)    //add date and time to message
                        ChatHistory.insert(ChatMessage(username, command, chatTime))
                        ChatHistory.notifyObservers(ChatMessage(username, command, chatTime).getJSonMessage())
                        ChatConsole().newMessage(ChatMessage(username, command, chatTime))
                        TopChatter.newMessage(ChatMessage(username, command, chatTime))
                    }
                }
            }
        } while (!exit)
    }

    private fun printChatters(chatterMap: Map<String, Int>) {
        println("Active user(s):")
        chatterMap.forEach { print("$it ") }
        println("")
        println("Top Chatters")
        val topChatterMap =
            chatterMap.toList().sortedByDescending { (_, value) -> value }.toMap()  //sort Map to find Top Chatters
        if (topChatterMap.size <= 4) {
            for (i in topChatterMap) {
                print("$i ")
            }
            println("")
        } else {
            var count = 1
            for (i in topChatterMap) {
                print("$i ")
                ++count
                //only print 4 top chatters
                if (count == 5) {
                    println("")
                    return
                }
            }
        }

    }

    private fun printMessageHistory() {
        for (message: ChatMessage in ChatHistory.returnMessageList()) {
            printOut.println(message.getMessageInOneLine())
        }
    }

    private fun printUserList(users: List<String>) {
        users.forEach { printOut.println(it) }
    }

    //shutdown and remove username
    private fun shutdown() {
        printChatters(TopChatter.getTopChatterList())
        ChatHistory.notifyObservers(ChatMessage(username, "has left the server", ""))
        client.close()
        Users.removeUsername(username)
        exit = true

    }

    //shutdown without remove user
    private fun quickShutdown() {
        client.close()
        exit = true
    }
}