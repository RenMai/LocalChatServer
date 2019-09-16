import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
@Serializable
class ChatMessage (val username: String, val message: String) {
    fun getMessageInOneLine(): String {
        return "$username: $message"
    }
    fun jSonMessage() {
        val aMessage = ChatMessage("Hi", "Hello")
        val abc = Json.stringify(ChatMessage.serializer(),aMessage)
    }
}
