package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils

/**
 * Created by evgen.ru79@gmail.com on 24.07.2019.
 */
data class Profile(
    val firstName: String,
    val lastName: String,
    val about: String,
    val repository: String,
    val rating: Int = 0,
    val respect: Int = 0
) {
    val nickName: String by lazy {
        Utils.transliteration("$firstName $lastName", "_") ?: "John Doe"
    }
    val rank: String = "Junior Android Developer"

    fun toMap() = mapOf(
        "nickName" to nickName,
        "rank" to rank,
        "firstName" to firstName,
        "lastName" to lastName,
        "about" to about,
        "repository" to repository,
        "rating" to rating,
        "respect" to respect
    )

    companion object {
        fun validateRepository(repositoryString: String): Boolean {
            val regexStr = "^(?:https://)?(?:www.)?(?:github.com/)[^/|\\s]+(?<!${getRegexExceptions()})(?:/)?$"
            val regex = Regex(regexStr)

            return !(repositoryString.isNotEmpty() && !regex.matches(repositoryString))

//            val normalizeUrl = repositoryString.toLowerCase().trim()
//            if (normalizeUrl.isEmpty())
//                return true
//
//            if (!normalizeUrl.matches("(https://)?(www.)?github.com/[a-z0-9\\-._]+(/)?".toRegex()))
//                return false
//            val githubNickname = normalizeUrl.substring(repositoryString.indexOf("github.com") + 11).dropLastWhile { it == '/' }
//            val excludes = setOf(
//                "enterprise",
//                "features",
//                "topics",
//                "collections",
//                "trending",
//                "events",
//                "marketplace",
//                "pricing",
//                "nonprofit",
//                "customer-stories",
//                "security",
//                "login",
//                "join"
//            )
//            return !excludes.contains(githubNickname)

        }

        private fun getRegexExceptions(): String {
            val exceptions = arrayOf(
                "enterprise", "features", "topics", "collections", "trending", "events", "marketplace", "pricing",
                "nonprofit", "customer-stories", "security", "login", "join"
            )
            return exceptions.joinToString("|\\b","\\b")
        }
    }
}