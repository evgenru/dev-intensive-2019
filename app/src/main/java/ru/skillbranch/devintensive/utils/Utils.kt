package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts = fullName?.trim()?.ifEmpty { null }?.split("\\s+".toRegex())
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)?.ifEmpty { null }
        return firstName to lastName
    }

    fun toInitials(firstName: String?, lastName: String?) =
        "${firstName?.trim()?.firstOrNull() ?: ""}${lastName?.trim()?.firstOrNull() ?: ""}"
            .toUpperCase()
            .ifEmpty { null }

    fun transliterationChar(char: Char): String? = when (char) {
        'а' -> "a"
        'б' -> "b"
        'в' -> "v"
        'г' -> "g"
        'д' -> "d"
        'е' -> "e"
        'ё' -> "e"
        'ж' -> "zh"
        'з' -> "z"
        'и' -> "i"
        'й' -> "i"
        'к' -> "k"
        'л' -> "l"
        'м' -> "m"
        'н' -> "n"
        'о' -> "o"
        'п' -> "p"
        'р' -> "r"
        'с' -> "s"
        'т' -> "t"
        'у' -> "u"
        'ф' -> "f"
        'х' -> "h"
        'ц' -> "c"
        'ч' -> "ch"
        'ш' -> "sh"
        'щ' -> "sh'"
        'ъ' -> ""
        'ы' -> "i"
        'ь' -> ""
        'э' -> "e"
        'ю' -> "yu"
        'я' -> "ya"
        else -> null
    }


    fun transliteration(payload: String?, divider: String = " "): String? {
        return payload?.map {
            when (it) {
                ' ' -> divider
                else -> transliterationChar(it) ?: transliterationChar(it.toLowerCase())?.capitalize() ?: it
            }
        }?.joinToString("")
    }


}