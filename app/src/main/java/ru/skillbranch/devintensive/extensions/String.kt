package ru.skillbranch.devintensive.extensions

/**
 * Created by evgen.ru79@gmail.com on 03.07.2019.
 */

fun String.truncate(length: Int = 16) =
    if (this.trimEnd().length > length)
        this.take(length+1).trimEnd().plus("...")
    else
        this.trimEnd()

fun String.stripHtml() =
    this
        .replace("(<script(\\s|\\S)*?<\\/script>)|(<style(\\s|\\S)*?<\\/style>)|(<!--(\\s|\\S)*?-->)|(<\\/?(\\s|\\S)*?>)".toRegex(), " ")
        .replace("&.{1,5};".toRegex(), " ")
        .replace("[\"&><\'\"]".toRegex()," ")
        .replace("\\s+".toRegex(), " ")
            .trim()
