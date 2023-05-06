package ru.netology

object ChatService {
    var currentUserId: Int = 0
    private var userPair = mutableListOf<Pair<Int, Int>>()
    var chats = mutableMapOf<MutableList<Pair<Int, Int>>, Chat>()

    fun clear() {
        currentUserId = 0
        userPair = mutableListOf()
        chats = mutableMapOf()
    }

    //Создание и проверка пары.
    private fun pair(userPair: MutableList<Pair<Int, Int>>): MutableList<Pair<Int, Int>> {
        for ((i) in userPair.withIndex()) {
            if (userPair[i].first > userPair[i].second) {
                userPair[i] = userPair[i].copy(first = userPair[i].second, second = userPair[i].first)
            }
        }
        return userPair
    }

    //Создание чата. Чат создаётся, когда текущий пользователь отправляет первое сообщение.
    //Если чат у пользователя уже есть, то сообщение добавляется к нему.
    //Все предыдущие сообщения считаются прочитанными.
    fun chatCreateAddMessage(pairUserId: Int, message: Message): Chat {
        userPair = mutableListOf(Pair(currentUserId, pairUserId))
        pair(userPair)
        return chats
            .getOrPut(userPair) { Chat() }
            .apply {
                messages.run {
                    asSequence()
                    add(message)
                }
                messages.last().authorMessageId = currentUserId
                messages.dropLast(1)
                    .forEach { it.readFlag = true }
            }
    }

    //Редактирование сообщения текущего пользователя.
    fun chatEditMessage(
        userPair: MutableList<Pair<Int, Int>>,
        currentUserId: Int,
        messageNumber: Int,
        messageText: String
    ): Boolean {
        pair(userPair)
        if (chats[userPair] == null) throw ChatNotFoundException("No chat")
        try {
            chats.filter { it.key == userPair }
                .values.asSequence()
                .filter { it.messages[messageNumber - 1].authorMessageId == currentUserId }
                .forEach { it.messages[messageNumber - 1].text = messageText }
        } catch (e: IndexOutOfBoundsException) {
            println("No message")
        }
        return true
    }

    //Удаление сообщения текущего пользователя.
    fun chatDeleteMessage(
        userPair: MutableList<Pair<Int, Int>>,
        currentUserId: Int,
        messageNumber: Int
    ): Boolean {
        pair(userPair)
        if (chats[userPair] == null) throw ChatNotFoundException("No chat")
        try {
            chats.filter { it.key == userPair }
                .values.asSequence()
                .filter { it.messages[messageNumber - 1].authorMessageId == currentUserId }
                .forEach { it.messages.removeAt(messageNumber - 1) }
        } catch (e: IndexOutOfBoundsException) {
            println("No message")
        }
        return true
    }

    //Удаление чата (целиком удаляется вся переписка).
    fun chatDelete(userPair: MutableList<Pair<Int, Int>>): Boolean {
        pair(userPair)
        if (chats[userPair] == null) throw ChatNotFoundException("No chat")
        chats.remove(userPair)
        return true
    }

    //Получение всех чатов текущего пользователя.
    fun getChats(currentUserId: Int): Map<MutableList<Pair<Int, Int>>, Chat> {
        return chats.filterKeys { it.first().first == currentUserId || it.first().second == currentUserId }
    }

    //Получение конкретного чата текущего пользователя.
    fun getChat(userPair: MutableList<Pair<Int, Int>>): Map<MutableList<Pair<Int, Int>>, Chat> {
        pair(userPair)
        if (chats[userPair] == null) throw ChatNotFoundException("No chat")
        return chats.filter { it.key == userPair }
    }

    /*Получение списка сообщений из чата текущего пользователя, указав:
    ключ чата;
    номер последнего сообщения, начиная с которого нужно подгрузить более новые;
    количество сообщений.
    После того как вызвана эта функция, все отданные сообщения автоматически считаются прочитанными.*/
    fun getListMessagesOfChat(userPair: MutableList<Pair<Int, Int>>, messageNumber: Int, count: Int): List<Message> {
        pair(userPair)
        if (chats[userPair] == null) throw ChatNotFoundException("No chat")
        try {
            return chats
                .getValue(userPair)
                .apply {
                    messages.run {
                        asSequence()
                        subList(messageNumber - 1, messageNumber + count)
                    }
                    messages.forEach { it.readFlag = true }
                }.messages
        } catch (e: IndexOutOfBoundsException) {
            println("Input error messageNumber or count")
        }
        return emptyList()
    }

    //Количество непрочитанных чатов текущего пользователя.
    //В каждом из таких чатов есть хотя бы одно непрочитанное сообщение.
    fun getUnreadChatsCount(currentUserId: Int): Int {
        val chatsCurrentUserId = getChats(currentUserId)
        if (chatsCurrentUserId.isEmpty()) throw ChatNotFoundException("No chat")
        return chatsCurrentUserId
            .values
            .count { (it.messages.lastOrNull()?.authorMessageId != currentUserId) && (!it.messages.last().readFlag) }
    }

    //Получение списка последних сообщений из чатов текущего пользователя.
    //Если сообщений в чате нет (все были удалены), то пишется «нет сообщений».
    fun getLastMessagesOfChats(currentUserId: Int): List<String> {
        val chatsCurrentUserId = getChats(currentUserId)
        if (chatsCurrentUserId.isEmpty()) throw ChatNotFoundException("No chat")
        return chatsCurrentUserId
            .values.asSequence()
            .map { it.messages.lastOrNull()?.text ?: "No messages" }
            .toList()
    }
}