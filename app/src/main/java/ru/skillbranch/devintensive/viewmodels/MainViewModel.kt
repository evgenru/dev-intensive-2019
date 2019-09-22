package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.repositories.ChatRepository

/**
 * Created by evgen.ru79@gmail.com on 07.09.2019.
 */
class MainViewModel : ViewModel() {
    private val chatRepository = ChatRepository
    private val chats: LiveData<List<ChatItem>> =
        Transformations.map(chatRepository.loadChats()) { chat ->
            return@map chat
                .filter { !it.isArchived }
                .map { it.toChatItem() }
                .sortedBy { it.id.toInt() }
        }

    fun getCharData(): LiveData<List<ChatItem>> = chats

    fun addToArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = true))
    }

    fun restoreFromArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = false))
    }
}