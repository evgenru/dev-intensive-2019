package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.extensions.humanizeDiff
import java.util.*

/**
 * Created by evgen.ru79@gmail.com on 27.06.2019.
 */
class TextMessage(
    id: String,
    from: User?,
    chat: Chat,
    date: Date,
    var text: String?,
    isIncoming: Boolean = false
) : BaseMessage(id, from, chat, isIncoming, date) {
    override fun formatMessage(): String {
        return "${from?.firstName} ${if (isIncoming) "получил" else "отправил"} сообщение \"$text\" ${date.humanizeDiff()}"
    }
}