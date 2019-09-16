import kotlinx.serialization.json.Json
import java.io.PrintWriter
import java.net.ServerSocket
import java.util.*
fun main() {
    ChatServer().serve()
}



/*fun main() {
    val ss = ServerSocket(30000, 2)

    while (true) {
        println("accepting")
        val s = ss.accept()
        println("accepted")

        val out = PrintWriter(s.getOutputStream())
        val ins = Scanner(s.getInputStream())

        Thread(CommandLineInterpreter(ins, out)).start()
    }
}

class CommandInterpreter(val ins: Scanner, val out: PrintWriter): Runnable {
    override fun run() {
        while (true) {
            out.println("Yessir?")
            out.flush()
            val line = ins.nextLine()
            out.println("I need $line")
            if (line.equals("bye")) {
                break
            }
        }
    }
}*/