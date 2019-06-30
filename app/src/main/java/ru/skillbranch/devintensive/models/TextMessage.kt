package ru.skillbranch.devintensive.models

import java.util.*

/**
 * Created by evgen.ru79@gmail.com on 27.06.2019.
 */
class TextMessage(
    id: String,
    from: User?,
    chat: Chat,
    isIncoming: Boolean = false,
    date: Date,
    var text: String?
) : BaseMessage(id, from, chat, isIncoming, date) {
    override fun formatMessage(): String {
        return "${from?.firstName} ${if (isIncoming) "получил" else "отправил"} сообщение \"$text\" $date"
    }
}