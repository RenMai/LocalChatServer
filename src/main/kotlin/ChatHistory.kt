object ChatHistory : ChatHistoryObservable {
    private val messageHistory: MutableList<ChatMessage> = mutableListOf()
    private val observers: MutableList<ChatHistoryObserver> = mutableListOf()
    override fun registerObserver(observer: ChatHistoryObserver) {
        observers.add(observer)
    }

    override fun deregisterObserver(observer: ChatHistoryObserver) {
        observers.remove(observer)
    }

    override fun notifyObservers(message: ChatMessage) {
        observers.forEach { it.newMessage(message) }
    }

    fun insert(message: ChatMessage) {
        messageHistory.add(message)
    }

    fun returnMessageList(): List<ChatMessage> {
        return messageHistory.toList()
    }
}