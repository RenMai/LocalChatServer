import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable @kotlinx.serialization.UnstableDefault
class ChatMessage(val username: String, val message: String, val chatTime: String) {
    fun getMessageInOneLine(): String {
        return "$chatTime $username: $message"
    }

    private fun jSonMessage(): String {
        return Json.stringify(serializer(), ChatMessage(username, message, chatTime))
    }

    fun getJSonMessage(): ChatMessage {
        return Json.parse(serializer(), jSonMessage())
    }
}
