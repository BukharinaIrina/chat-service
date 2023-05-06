package ru.netology

object ChatService {
    private var chats = mutableMapOf<Int, Chat>()

    fun clear() {
        chats = mutableMapOf()
    }

    //Создание чата. Чат создаётся, когда пользователю отправляется первое сообщение.
    //Если чат у пользователя уже есть, то сообщение добавляется к нему.
    fun chatCreateAddMessage(userId: Int, message: Message): Chat {
        return chats.getOrPut(userId) { Chat() }.apply { messages.add(message) }
    }

    // Редактирование сообщения.
    fun chatEditMessage(userId: Int, messageNumber: Int): Boolean {
        if (chats[userId] == null) {
            throw ChatNotFoundException("No chat with user id $userId")
        }
        try {
            chats.filter { it.key == userId }.values.forEach { it.messages[messageNumber - 1].text = "MESSAGE_2" }
        } catch (e: IndexOutOfBoundsException) {
            println("No message $messageNumber with user chat id $userId")
        }
        return true
    }

    //Удаление сообщения.
    fun chatDeleteMessage(userId: Int, messageNumber: Int): Boolean {
        try {
            chats[userId]?.messages?.removeAt(messageNumber - 1)
                ?: throw ChatNotFoundException("No chat with user id $userId")
        } catch (e: IndexOutOfBoundsException) {
            println("No message $messageNumber with user chat id $userId")
        }
        return true
    }

    //Удаление чата (целиком удаляется вся переписка).
    fun chatDelete(userId: Int): Boolean {
        if (chats[userId] == null) {
            throw ChatNotFoundException("No chat with user id $userId")
        }
        chats.remove(userId)
        return true
    }

    //Получение списка чатов.
    fun getChats() {
        displayMap(chats)
    }

    //Получение чата пользователя.
    fun getChat(userId: Int): List<Pair<Int, Chat>> {
        if (chats[userId] == null) {
            throw ChatNotFoundException("No chat with user id $userId")
        }
        return chats.filter { it.key == userId }.toList()
    }

    /*Получение списка сообщений из чата, указав:
   номер чата;
   номер последнего сообщения, начиная с которого нужно подгрузить более новые;
   количество сообщений.
   После того как вызвана эта функция, все отданные сообщения автоматически считаются прочитанными.*/
    fun getListMessagesOfChat(userId: Int, messageNumber: Int, count: Int): List<Message> {
        try {
            val list = chats[userId]?.messages?.subList(messageNumber - 1, messageNumber + count)
            list?.forEach { it.readFlag = true }
                ?: throw ChatNotFoundException("No chat with user id $userId")
            return list
        } catch (e: IndexOutOfBoundsException) {
            println("Input error messageNumber or count")
        }
        return emptyList()
    }

    //Количество непрочитанных чатов.
    //В каждом из таких чатов есть хотя бы одно непрочитанное сообщение.
    fun getUnreadChatsCount(): Int {
        var count = 0
        if (chats.isNotEmpty()) {
            count = chats.values.count { it.messages.count { !it.readFlag } != 0 }
        }
        return count
    }

    //Получение списка последних сообщений из чатов.
    //Если сообщений в чате нет (все были удалены), то пишется «нет сообщений».
    fun getLastMessagesOfChats(): List<String> {
        return chats.values.map { it.messages.lastOrNull()?.text ?: "No messages" }
    }
}