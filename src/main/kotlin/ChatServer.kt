import java.net.*
class ChatServer {
    fun serve()
    {
        try {
            val serverSocket = ServerSocket(23)
            println("We have a chat server at port: " + serverSocket.localPort)
            while (true)
            {
                val s = serverSocket.accept()
                println("New connection " + s.inetAddress.hostAddress + " " + s.port)
                Thread(ChatConnector(s.getInputStream(), s.getOutputStream(), client = s)).start()
            }
        } catch (err: Exception)
        {
            println("Error : $err")
        }
    }
}