package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

/**
 * Created by evgen.ru79@gmail.com on 27.06.2019.
 */
fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy") = SimpleDateFormat(pattern, Locale("ru")).format(this)

fun Date.add(value: Int, units: TimeUnits): Date {
    return this.apply { time += value * units.mills }
}

enum class TimeUnits(val mills: Long) {
    SECOND(1000L),
    MINUTE(60 * SECOND.mills),
    HOUR(60 * MINUTE.mills),
    DAY(24 * HOUR.mills);

    fun plural(value: Int) =
        "$value ${when (this) {
            SECOND -> getDeclensions(value, "секунд", "секунд", "секунду", "секунды", "секунд")
            MINUTE -> getDeclensions(value, "минут", "минут", "минуту", "минуты", "минут")
            HOUR -> getDeclensions(value, "часов", "часов", "час", "часа", "часов")
            DAY -> getDeclensions(value, "дней", "дней", "день", "дня", "дней")
        }}"

    private fun getDeclensions(
        value: Int,
        unitNameThs: String,
        unitName0: String,
        unitName1: String,
        unitName234: String,
        unitName: String
    ) =
        when (value) {
            in 11..19 -> unitNameThs
            else -> when (value.rem(10)) {
                0 -> unitName0
                1 -> unitName1
                in 2..4 -> unitName234
                else -> unitName
            }
        }

}


fun Date.humanizeDiff(date: Date = Date()): String {

    val diffTime = (this.time - date.time)

    return when {
        diffTime > 360 * TimeUnits.DAY.mills -> "более чем через год"
        diffTime < -360 * TimeUnits.DAY.mills -> "более года назад"
        diffTime.absoluteValue in 0..1 * TimeUnits.SECOND.mills -> "только что"
        else -> {
            val humanizeTime = when (diffTime.absoluteValue) {
                in 1 * TimeUnits.SECOND.mills..45 * TimeUnits.SECOND.mills -> "несколько секунд"
                in 45 * TimeUnits.SECOND.mills..75 * TimeUnits.SECOND.mills -> "минуту"
                in 75 * TimeUnits.SECOND.mills..45 * TimeUnits.MINUTE.mills -> {
                    TimeUnits.MINUTE.plural((diffTime.absoluteValue / TimeUnits.MINUTE.mills).toInt())
                }
                in 45 * TimeUnits.MINUTE.mills..75 * TimeUnits.MINUTE.mills -> "час"
                in 75 * TimeUnits.MINUTE.mills..22 * TimeUnits.HOUR.mills -> {
                    TimeUnits.HOUR.plural((diffTime.absoluteValue / TimeUnits.HOUR.mills).toInt())
                }
                in 22 * TimeUnits.HOUR.mills..26 * TimeUnits.HOUR.mills -> "день"
                in 26 * TimeUnits.HOUR.mills..360 * TimeUnits.DAY.mills -> {
                    TimeUnits.DAY.plural((diffTime.absoluteValue / TimeUnits.DAY.mills).toInt())
                }
                else -> ""
            }

            if (this < date)
                "$humanizeTime назад"
            else
                "через $humanizeTime"
        }
    }

}
