package ru.netology

data class Chat(
    var messages: MutableList<Message> = mutableListOf()
)
