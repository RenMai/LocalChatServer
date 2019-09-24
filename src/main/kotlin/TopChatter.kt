@kotlinx.serialization.UnstableDefault
object TopChatter : ChatHistoryObserver {
    private val countMessage = mutableMapOf<String, Int>()

    override fun newMessage(chatMessage: ChatMessage) {
        if (!countMessage.containsKey(chatMessage.username)) {
            countMessage[chatMessage.username] = 0   //init the username in Map
        } else {
            var count = countMessage[chatMessage.username]!!
            countMessage[chatMessage.username] = ++count //Start counting
        }
    }

    fun getTopChatterList(): Map<String, Int> {
        return countMessage.toMap()
    }
}