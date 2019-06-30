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
    DAY(24 * HOUR.mills)
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
                    val minutes = (diffTime.absoluteValue / TimeUnits.MINUTE.mills).toInt()
                    "$minutes ${when (minutes) {
                        in 11..19 -> "минут"
                        else -> when (minutes.rem(10)) {
                            0 -> "минут"
                            1 -> "минуту"
                            in 2..4 -> "минуты"
                            else -> "минут"
                        }
                    }}"
                }
                in 45 * TimeUnits.MINUTE.mills..75 * TimeUnits.MINUTE.mills -> "час"
                in 75 * TimeUnits.MINUTE.mills..22 * TimeUnits.HOUR.mills -> {
                    val hours = (diffTime.absoluteValue / TimeUnits.HOUR.mills).toInt()
                    "$hours ${when (hours) {
                        in 11..19 -> "часов"
                        else -> when (hours.rem(10)) {
                            0 -> "часов"
                            1 -> "час"
                            in 2..4 -> "часа"
                            else -> "часов"
                        }
                    }}"
                }
                in 22 * TimeUnits.HOUR.mills..26 * TimeUnits.HOUR.mills -> "день"
                in 26 * TimeUnits.HOUR.mills..360 * TimeUnits.DAY.mills -> {
                    val days = (diffTime.absoluteValue / TimeUnits.DAY.mills).toInt()
                    "$days ${when (days) {
                        in 11..19 -> "дней"
                        else -> when (days.rem(10)) {
                            0 -> "дней"
                            1 -> "день"
                            in 2..4 -> "дня"
                            else -> "дней"
                        }
                    }}"
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
