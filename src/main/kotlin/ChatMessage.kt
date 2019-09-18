import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
class ChatMessage (val username: String, val message: String) {
    fun getMessageInOneLine(): String {
        return "$username: $message"
    }
    private fun jSonMessage() : String
    {
        return Json.stringify(serializer(),ChatMessage(username, message))
    }
    fun getJSonMessage(): ChatMessage
    {
        return Json.parse(serializer(), jSonMessage())
    }
}
