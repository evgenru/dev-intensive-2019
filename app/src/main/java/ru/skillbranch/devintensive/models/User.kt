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

        private var firstName: String? = null
        private var lastName: String? = null
        private var avatar: String? = null
        private var rating: Int = 0
        private var respect: Int = 0
        private var lastVisit: Date? = Date()
        private var isOnline: Boolean = false

        fun firstName(_firstName: String?):Factory {
            firstName = _firstName
            return Factory
        }

        fun lastName(_lastName: String?):Factory {
            lastName = _lastName
            return Factory
        }

        fun avatar(_avatar: String?):Factory {
            avatar = _avatar
            return Factory
        }

        fun rating(_rating: Int):Factory {
            rating = _rating
            return Factory
        }

        fun respect(_respect: Int):Factory {
            respect = _respect
            return Factory
        }

        fun lastVisit(_lastVisit: Date?):Factory {
            lastVisit = _lastVisit
            return Factory
        }

        fun isOnline(_isOnline: Boolean):Factory {
            isOnline = _isOnline
            return Factory
        }

        fun build():User {
            lastId++
            val user = User("$lastId", firstName, lastName, avatar, rating, respect, lastVisit, isOnline)
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

