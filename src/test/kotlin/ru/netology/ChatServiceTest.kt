package ru.netology

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ChatServiceTest {

    @Before
    fun clearBeforeTest() {
        ChatService.clear()
    }

    @Test
    fun chatCreateAddMessage_createAdd() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        ChatService.currentUserId = 2
        ChatService.chatCreateAddMessage(1, Message(text = "message"))
        val result = ChatService.getChats(1)
        assertNotNull(result)
    }

    @Test
    fun chatEditMessage_edit() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        val result = ChatService.chatEditMessage(
            mutableListOf(Pair(2, 1)),
            1, 1, "newMessageText"
        )
        assertTrue(result)
    }

    @Test(expected = ChatNotFoundException::class)
    fun chatEditMessage_noChat() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        ChatService.chatEditMessage(
            mutableListOf(Pair(2, 3)),
            1, 1, "newMessageText"
        )
    }

    @Test
    fun chatEditMessage_noAuthorMessage() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        val result = ChatService.chatEditMessage(
            mutableListOf(Pair(2, 1)),
            8, 1, "newMessageText"
        )
        assertTrue(result)
    }

    @Test
    fun chatEditMessage_noMessage() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        val result = ChatService.chatEditMessage(
            mutableListOf(Pair(2, 1)),
            1, 3, "newMessageText"
        )
        assertTrue(result)
    }

    @Test
    fun chatDeleteMessage_delete() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        val result = ChatService.chatDeleteMessage(
            mutableListOf(Pair(2, 1)),
            1, 1
        )
        assertTrue(result)
    }

    @Test(expected = ChatNotFoundException::class)
    fun chatDeleteMessage_noChat() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        ChatService.chatDeleteMessage(
            mutableListOf(Pair(2, 3)),
            1, 1
        )
    }

    @Test
    fun chatDeleteMessage_noAuthorMessage() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        val result = ChatService.chatDeleteMessage(
            mutableListOf(Pair(2, 1)),
            8, 1
        )
        assertTrue(result)
    }

    @Test
    fun chatDeleteMessage_noMessage() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        val result = ChatService.chatDeleteMessage(
            mutableListOf(Pair(2, 1)),
            1, 3
        )
        assertTrue(result)
    }

    @Test
    fun chatDelete_delete() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        val result = ChatService.chatDelete(mutableListOf(Pair(1, 2)))
        assertTrue(result)
    }

    @Test(expected = ChatNotFoundException::class)
    fun chatDelete_noChat() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        ChatService.chatDelete(mutableListOf(Pair(1, 3)))
    }

    @Test
    fun getChats_get() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        ChatService.currentUserId = 2
        ChatService.chatCreateAddMessage(1, Message(text = "message"))
        ChatService.currentUserId = 2
        ChatService.chatCreateAddMessage(3, Message(text = "message"))
        val chat = mutableMapOf(
            Pair(
                mutableListOf(Pair(1, 2)),
                Chat(
                    messages = mutableListOf(
                        Message(1, "message", true),
                        Message(2, "message", false)
                    )
                )
            ),
            Pair(
                mutableListOf(Pair(2, 3)),
                Chat(
                    messages = mutableListOf(
                        Message(2, "message", false)
                    )
                )
            )
        )
        val result = ChatService.getChats(2)
        assertEquals(chat, result)
    }

    @Test
    fun getChats_noChats() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        ChatService.currentUserId = 2
        ChatService.chatCreateAddMessage(1, Message(text = "message"))
        ChatService.currentUserId = 2
        ChatService.chatCreateAddMessage(3, Message(text = "message"))
        ChatService.chatDelete(mutableListOf(Pair(2, 3)))
        val chat = mutableMapOf<MutableList<Pair<Int, Int>>, Chat>()
        val result = ChatService.getChats(3)
        assertEquals(chat, result)
    }

    @Test
    fun getChat_get() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        ChatService.currentUserId = 2
        ChatService.chatCreateAddMessage(1, Message(text = "message"))
        ChatService.currentUserId = 2
        ChatService.chatCreateAddMessage(3, Message(text = "message"))
        val chat = mutableMapOf(
            Pair(
                mutableListOf(Pair(2, 3)),
                Chat(
                    messages = mutableListOf(
                        Message(2, "message", false)
                    )
                )
            )
        )
        val result = ChatService.getChat(mutableListOf(Pair(2, 3)))
        assertEquals(chat, result)
    }

    @Test(expected = ChatNotFoundException::class)
    fun getChat_noChat() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        ChatService.currentUserId = 2
        ChatService.chatCreateAddMessage(1, Message(text = "message"))
        ChatService.currentUserId = 2
        ChatService.chatCreateAddMessage(3, Message(text = "message"))
        ChatService.getChat(mutableListOf(Pair(2, 4)))
    }

    @Test
    fun getListMessagesOfChat_list() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        ChatService.currentUserId = 2
        ChatService.chatCreateAddMessage(1, Message(text = "message"))
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        val list = listOf(
            Message(1, "message", true),
            Message(2, "message", true),
            Message(1, "message", true)
        )
        val result = ChatService.getListMessagesOfChat(mutableListOf(Pair(1, 2)), 1, 2)
        assertEquals(list, result)
    }

    @Test(expected = ChatNotFoundException::class)
    fun getListMessagesOfChat_noChat() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        ChatService.chatDelete(mutableListOf(Pair(1, 2)))
        ChatService.getListMessagesOfChat(mutableListOf(Pair(1, 2)), 1, 2)
    }

    @Test
    fun getListMessagesOfChat_errorMessageOrCount() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        ChatService.currentUserId = 2
        ChatService.chatCreateAddMessage(1, Message(text = "message"))
        val list = listOf<Message>()
        val result = ChatService.getListMessagesOfChat(mutableListOf(Pair(1, 2)), 4, 7)
        assertEquals(list, result)
    }

    @Test
    fun getUnreadChatsCount_flagsTrue() {
        ChatService.currentUserId = 2
        ChatService.chatCreateAddMessage(1, Message(text = "message", readFlag = true))
        ChatService.currentUserId = 2
        ChatService.chatCreateAddMessage(3, Message(text = "message", readFlag = true))
        ChatService.currentUserId = 3
        ChatService.chatCreateAddMessage(2, Message(text = "message", readFlag = true))
        val result = ChatService.getUnreadChatsCount(2)
        assertEquals(0, result)
    }

    @Test
    fun getUnreadChatsCount_flagsFalse() {
        ChatService.currentUserId = 2
        ChatService.chatCreateAddMessage(1, Message(text = "message"))
        ChatService.currentUserId = 2
        ChatService.chatCreateAddMessage(3, Message(text = "message"))
        ChatService.currentUserId = 3
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        val result = ChatService.getUnreadChatsCount(2)
        assertEquals(1, result)
    }

    @Test
    fun getUnreadChatsCount_lastMessagesNoCurrentUserId() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        ChatService.currentUserId = 2
        ChatService.chatCreateAddMessage(1, Message(text = "message"))
        ChatService.currentUserId = 2
        ChatService.chatCreateAddMessage(3, Message(text = "message"))
        val result = ChatService.getUnreadChatsCount(2)
        assertEquals(0, result)
    }

    @Test(expected = ChatNotFoundException::class)
    fun getUnreadChatsCount_noChats() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        ChatService.getUnreadChatsCount(3)
    }

    @Test
    fun getLastMessagesOfChats_messages() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        ChatService.currentUserId = 2
        ChatService.chatCreateAddMessage(1, Message(text = "message"))
        ChatService.currentUserId = 2
        ChatService.chatCreateAddMessage(3, Message(text = "message"))
        val string = listOf("message", "message")
        val result = ChatService.getLastMessagesOfChats(2)
        assertEquals(string, result)
    }

    @Test
    fun getLastMessagesOfChats_noMessages() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        ChatService.currentUserId = 2
        ChatService.chatCreateAddMessage(3, Message(text = "message"))
        ChatService.chatDeleteMessage(mutableListOf(Pair(3, 2)), 2, 1)
        val string = listOf("message", "No messages")
        val result = ChatService.getLastMessagesOfChats(2)
        assertEquals(string, result)
    }

    @Test(expected = ChatNotFoundException::class)
    fun getLastMessagesOfChats_noChats() {
        ChatService.currentUserId = 1
        ChatService.chatCreateAddMessage(2, Message(text = "message"))
        ChatService.getLastMessagesOfChats(3)
    }
}