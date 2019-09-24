class ChatConsole : ChatHistoryObserver {
    //print the message to console
    override fun newMessage(message: ChatMessage) {
        println(message.getMessageInOneLine())
    }
}