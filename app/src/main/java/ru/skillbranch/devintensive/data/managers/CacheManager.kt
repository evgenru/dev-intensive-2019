package ru.skillbranch.devintensive.data.managers

import androidx.lifecycle.MutableLiveData
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.User
import ru.skillbranch.devintensive.utils.DataGenerator

object CacheManager {
    private val chats = MutableLiveData<List<Chat>>().apply { value = DataGenerator.stabChats }
    private val users = MutableLiveData<List<User>>().apply { value =  DataGenerator.stabUsers }

    fun loadChats(): MutableLiveData<List<Chat>> {
        return chats
    }

    fun findUsersByIds(ids: List<String>): List<User> {
        return users.value!!.filter { ids.contains(it.id) }
    }

    fun nextChatId():String{
        return "${chats.value!!.size}"
    }

    fun insertChat(chat: Chat){
        chats.value = chats.value!!.plus(chat)
    }
}
