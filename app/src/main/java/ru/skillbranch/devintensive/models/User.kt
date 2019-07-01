package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

/**
 * Created by evgen.ru79@gmail.com on 27.06.2019.
 */
data class User(
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String? = null,
    var rating: Int = 0,
    var respect: Int = 0,
    var lastVisit: Date? = Date(),
    var isOnline: Boolean = false
) {

    companion object Factory {
        private var lastId = -1
        fun makeUser(fullName: String?): User {
            lastId++
            val (firstName, lastName) = Utils.parseFullName(fullName)
            return User("$lastId", firstName, lastName)
        }
    }

    class Builder {
        private var id: String = ""
        private var firstName: String? = null
        private var lastName: String? = null
        private var avatar: String? = null
        private var rating: Int = 0
        private var respect: Int = 0
        private var lastVisit: Date? = Date()
        private var isOnline: Boolean = false

        fun id(_id: String) = this.apply { id = _id }

        fun firstName(_firstName: String?) = this.apply { firstName = _firstName }

        fun lastName(_lastName: String?) = this.apply { lastName = _lastName }

        fun avatar(_avatar: String?) = this.apply { avatar = _avatar }

        fun rating(_rating: Int) = this.apply { rating = _rating }

        fun respect(_respect: Int) = this.apply { respect = _respect }

        fun lastVisit(_lastVisit: Date?) = this.apply { lastVisit = _lastVisit }

        fun isOnline(_isOnline: Boolean) = this.apply { isOnline = _isOnline }

        fun build(): User {
            val user = User(id, firstName, lastName, avatar, rating, respect, lastVisit, isOnline)
            id = ""
            firstName = null
            lastName = null
            avatar = null
            rating = 0
            respect = 0
            lastVisit = Date()
            isOnline = false
            return user
        }


    }
}

