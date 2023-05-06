package ru.netology

fun main() {
    ChatService.chatCreateAddMessage(1, Message("message_1"))
    ChatService.chatCreateAddMessage(1, Message("message_2"))
    ChatService.chatCreateAddMessage(1, Message("message_3"))
    ChatService.chatCreateAddMessage(2, Message("message_1"))
    ChatService.chatCreateAddMessage(2, Message("message_2"))
    ChatService.chatCreateAddMessage(3, Message("message_1"))
    ChatService.getChats()
    println()

    displayList(ChatService.getChat(2))
    println()

    ChatService.chatEditMessage(1, 2)
    displayList(ChatService.getChat(1))
    println()

    println(ChatService.getUnreadChatsCount())
    println()

    displayList(ChatService.getListMessagesOfChat(1, 1, 1))
    println()

    println(ChatService.getLastMessagesOfChats())
    println()

    ChatService.chatDeleteMessage(2, 1)
    displayList(ChatService.getChat(2))
    ChatService.chatDeleteMessage(2, 1)
    displayList(ChatService.getChat(2))
    println()

    println(ChatService.getLastMessagesOfChats())
    println()

    ChatService.chatDelete(3)
    ChatService.getChats()
    println()

    println(ChatService.getUnreadChatsCount())
    println()
}




