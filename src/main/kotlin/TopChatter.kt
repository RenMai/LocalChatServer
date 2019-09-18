class TopChatter:ChatHistoryObserver {
    override fun newMessage(message: ChatMessage) {
    }
    var countMessage:Int=0
    private val topChatter: MutableMap<String,Int> = mutableMapOf()
    init {
        ChatHistory.registerObserver(this)
    }
    fun initTopChatter(username: String)
    {
        topChatter[username] = countMessage
    }
    fun addTopChatter(username: String)
    {
        topChatter[username]=++countMessage
    }
}