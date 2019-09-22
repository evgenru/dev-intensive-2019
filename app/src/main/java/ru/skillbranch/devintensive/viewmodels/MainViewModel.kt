package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.*
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.models.data.ChatType
import ru.skillbranch.devintensive.repositories.ChatRepository

/**
 * Created by evgen.ru79@gmail.com on 07.09.2019.
 */
class MainViewModel : ViewModel() {
    private val chatRepository = ChatRepository
    private val chats: LiveData<List<ChatItem>> =
        Transformations.map(chatRepository.loadChats()) { chat ->
            val result = chat
                .filter { !it.isArchived }
                .map { it.toChatItem() }
                .sortedBy { it.id.toInt() }
                .toMutableList()

            val archiveChats = chat
                .filter { it.isArchived }

            if (archiveChats.isNotEmpty()) {
                val archiveChat = Chat(
                    ARCHIVE_CHAT_ID,
                    "",
                    messages = archiveChats.map { it.messages }.flatten().toMutableList(),
                    isArchived = true
                )
                result.add(0, archiveChat.toChatItem())
            }

            return@map result
        }
    private val query = MutableLiveData<String>("")

    fun getCharData(): LiveData<List<ChatItem>> {
        val result = MediatorLiveData<List<ChatItem>>()

        val filterF = {
            val queryStr = query.value!!
            val chatList = chats.value!!

            result.value = if (queryStr.isEmpty()) chatList
            else chatList.filter { it.title.contains(queryStr, ignoreCase = true) }
        }

        result.addSource(chats) { filterF.invoke() }
        result.addSource(query) { filterF.invoke() }

        return result
    }

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

    fun handleSearchQuery(text: String) {
        query.value = text
    }

    companion object {
        const val ARCHIVE_CHAT_ID: String = "ARCHIVE_CHAT_ID"
    }
}