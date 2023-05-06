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
        val result = ChatService.chatCreateAddMessage(1, Message("message_1", false))
        assertNotNull(result)
    }

    @Test
    fun chatEditMessage_edit() {
        ChatService.chatCreateAddMessage(1, Message("message_1", false))
        ChatService.chatCreateAddMessage(1, Message("message_2", false))
        val result = ChatService.chatEditMessage(1,2)
        assertTrue(result)
    }

    @Test(expected = ChatNotFoundException::class)
    fun chatEditMessage_noChat() {
        ChatService.chatCreateAddMessage(1, Message("message_1", false))
        ChatService.chatEditMessage(2,1)
    }

    @Test
    fun chatDeleteMessage_delete() {
        ChatService.chatCreateAddMessage(1, Message("message_1", false))
        ChatService.chatCreateAddMessage(1, Message("message_2", false))
        ChatService.chatCreateAddMessage(2, Message("message_1", false))
        ChatService.chatCreateAddMessage(2, Message("message_2", false))
        val result = ChatService.chatDeleteMessage(2,1)
        assertTrue(result)
    }

    @Test(expected = ChatNotFoundException::class)
    fun chatDeleteMessage_noChat() {
        ChatService.chatCreateAddMessage(1, Message("message_1", false))
        ChatService.chatCreateAddMessage(2, Message("message_1", false))
        ChatService.chatDeleteMessage(3,1)
    }

    @Test
    fun chatDelete_delete() {
        ChatService.chatCreateAddMessage(1, Message("message_1", false))
        val result = ChatService.chatDelete(1)
        assertTrue(result)
    }

    @Test(expected = ChatNotFoundException::class)
    fun chatDelete_noChat() {
        ChatService.chatCreateAddMessage(1, Message("message_1", false))
        ChatService.chatDelete(2)
    }

    @Test
    fun getChats_get() {
        ChatService.chatCreateAddMessage(1, Message("message_1", false))
        ChatService.chatCreateAddMessage(2, Message("message_1", false))
        val result = ChatService.getChats()
        assertNotNull(result)
    }

    @Test
    fun getChat_get() {
        ChatService.chatCreateAddMessage(1, Message("message_1", false))
        ChatService.chatCreateAddMessage(2, Message("message_1", false))
        ChatService.chatCreateAddMessage(3, Message("message_1", false))
        val result = ChatService.getChat(2)
        assertNotNull(result)
    }

    @Test(expected = ChatNotFoundException::class)
    fun getChat_noChat() {
        ChatService.chatCreateAddMessage(1, Message("message_1", false))
        ChatService.getChat(2)
    }

    @Test
    fun getListMessagesOfChat_list() {
        ChatService.chatCreateAddMessage(1, Message("message_1", false))
        ChatService.chatCreateAddMessage(1, Message("message_2", false))
        ChatService.chatCreateAddMessage(1, Message("message_3", false))
        val list = listOf(Message("message_1", true),
            Message("message_2", true),Message("message_3", true))
        val result = ChatService.getListMessagesOfChat(1,1,2)

        assertEquals(list, result)
    }

    @Test(expected = ChatNotFoundException::class)
    fun getListMessagesOfChat_noChat() {
        ChatService.chatCreateAddMessage(1, Message("message_1", false))
        ChatService.chatCreateAddMessage(1, Message("message_2", false))
        ChatService.chatCreateAddMessage(1, Message("message_3", false))
        ChatService.getListMessagesOfChat(2,1,2)
    }

    @Test
    fun getUnreadChatsCount_count() {
        ChatService.chatCreateAddMessage(1, Message("message_1", true))
        ChatService.chatCreateAddMessage(1, Message("message_2", false))
        ChatService.chatCreateAddMessage(2, Message("message_1", false))
        ChatService.chatCreateAddMessage(3, Message("message_1", true))
        val result = ChatService.getUnreadChatsCount()
        assertEquals(2, result)
    }

    @Test
    fun getUnreadChatsCount_noChats() {
        ChatService.chatCreateAddMessage(1, Message("message_1", true))
        ChatService.chatDelete(1)
        val result = ChatService.getUnreadChatsCount()
        assertEquals(0, result)
    }

    @Test
    fun getLastMessagesOfChats() {
        ChatService.chatCreateAddMessage(1, Message("message_1", true))
        ChatService.chatCreateAddMessage(1, Message("message_2", false))
        ChatService.chatCreateAddMessage(2, Message("message_1", false))
        ChatService.chatCreateAddMessage(3, Message("message_1", true))
        val string = listOf("message_2", "message_1", "message_1")
        val result = ChatService.getLastMessagesOfChats()
        assertEquals(string, result)
    }

    @Test
    fun getLastMessagesOfChats_noMessages() {
        ChatService.chatCreateAddMessage(1, Message("message_1", true))
        ChatService.chatCreateAddMessage(1, Message("message_2", false))
        ChatService.chatCreateAddMessage(2, Message("message_1", false))
        ChatService.chatDeleteMessage(2,1)
        val string = listOf("message_2", "No messages")
        val result = ChatService.getLastMessagesOfChats()
        assertEquals(string, result)
    }
}
