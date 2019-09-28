package ru.skillbranch.devintensive.viewmodels

import android.util.Log
import androidx.lifecycle.*
import ru.skillbranch.devintensive.models.data.ChatItem

/**
 * Created by evgen.ru79@gmail.com on 07.09.2019.
 */
class ArchiveViewModel : MainViewModel() {
    override val chats: LiveData<List<ChatItem>> = Transformations.map(chatRepository.loadChats()) { chat ->
            Log.d("TEST", "ArchiveViewModel: ${chat?.size}")
            return@map chat
                .filter { it.isArchived }
                .map { it.toChatItem() }
                .sortedBy { it.id.toInt() }
                .apply {
                    Log.d("TEST", "ArchiveViewModel_2: ${this.size}")
                }
        }
}