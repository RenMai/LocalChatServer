object TopChatter : ChatHistoryObserver {
    private val messageCountMap = mutableMapOf<String, Int>()

    override fun newMessage(chatMessage: ChatMessage) {
        if (!messageCountMap.containsKey(chatMessage.username)) {
            messageCountMap[chatMessage.username] = 0   //init the username in Map
        } else {
            var count = messageCountMap[chatMessage.username]!!
            messageCountMap[chatMessage.username] = ++count //Start counting
        }
    }

    fun getTopChatterList(): Map<String, Int> {
        return messageCountMap.toMap()
    }
}