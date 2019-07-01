package ru.skillbranch.devintensive.models

import java.util.*

/**
 * Created by evgen.ru79@gmail.com on 27.06.2019.
 */
abstract class BaseMessage(
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming: Boolean = false,
    val date: Date = Date()
) {
    abstract fun formatMessage(): String

    companion object AbstractFactory {
        private var lastId = -1
        fun makeMessage(
            from: User?,
            chat: Chat,
            date: Date = Date(),
            type: String = "text",
            payload: Any? = null,
            isIncoming: Boolean = false
        ): BaseMessage {
            lastId++
            return when (type) {
                "image" -> ImageMessage("$lastId", from, chat, date, payload as String, isIncoming = isIncoming)
                else -> TextMessage("$lastId", from, chat, date, payload as String, isIncoming = isIncoming)
            }
        }
    }
}