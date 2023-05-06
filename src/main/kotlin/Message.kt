package ru.netology

data class Message(
    var authorMessageId: Int = 0,
    var text: String,
    var readFlag: Boolean = false
)