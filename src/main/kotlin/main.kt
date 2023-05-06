package ru.netology

fun main() {
    ChatService.currentUserId = 1
    ChatService.chatCreateAddMessage(2, Message(text = "message"))
    ChatService.currentUserId = 2
    ChatService.chatCreateAddMessage(1, Message(text = "message"))
    ChatService.currentUserId = 1
    ChatService.chatCreateAddMessage(2, Message(text = "message"))

    ChatService.currentUserId = 2
    ChatService.chatCreateAddMessage(3, Message(text = "message"))
    ChatService.currentUserId = 3
    ChatService.chatCreateAddMessage(2, Message(text = "message"))
    ChatService.currentUserId = 2
    ChatService.chatCreateAddMessage(3, Message(text = "message"))

    ChatService.currentUserId = 3
    ChatService.chatCreateAddMessage(1, Message(text = "message"))
    ChatService.currentUserId = 1
    ChatService.chatCreateAddMessage(3, Message(text = "message"))
    ChatService.currentUserId = 3
    ChatService.chatCreateAddMessage(1, Message(text = "message"))

    ChatService.currentUserId = 2
    ChatService.chatCreateAddMessage(1, Message(text = "message"))

    displayMap(ChatService.chats)
    println()

    ChatService.currentUserId = 2
    ChatService.chatEditMessage(mutableListOf(Pair(2, 3)), 2, 1, "newMessageText")
    ChatService.currentUserId = 3
    ChatService.chatEditMessage(mutableListOf(Pair(2, 3)), 3, 2, "newMessageText")
    displayMap(ChatService.getChat(mutableListOf(Pair(3, 2))))
    println()

    displayMap(ChatService.getChats(1))
    println(ChatService.getUnreadChatsCount(1))
    println(ChatService.getLastMessagesOfChats(1))
    displayList(ChatService.getListMessagesOfChat(mutableListOf(Pair(1, 2)), 1, 3))
    println()

    ChatService.currentUserId = 3
    ChatService.chatDeleteMessage(mutableListOf(Pair(1, 3)), 3, 3)
    displayMap(ChatService.getChat(mutableListOf(Pair(3, 1))))
    ChatService.chatDeleteMessage(mutableListOf(Pair(1, 3)), 3, 1)
    displayMap(ChatService.getChat(mutableListOf(Pair(3, 1))))
    ChatService.currentUserId = 1
    ChatService.chatDeleteMessage(mutableListOf(Pair(1, 3)), 1, 1)
    displayMap(ChatService.getChat(mutableListOf(Pair(3, 1))))
    println()

    displayMap(ChatService.getChats(3))
    println(ChatService.getLastMessagesOfChats(3))
    println()

    ChatService.currentUserId = 3
    ChatService.chatDelete(mutableListOf(Pair(3, 1)))
    displayMap(ChatService.chats)
    println(ChatService.getUnreadChatsCount(2))
    println()
}



