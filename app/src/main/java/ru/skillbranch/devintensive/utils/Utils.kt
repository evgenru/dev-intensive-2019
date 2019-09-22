package ru.skillbranch.devintensive.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import ru.skillbranch.devintensive.R

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

    private fun transliterationChar(char: Char): String? = when (char) {
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
        return payload?.trim()?.map {
            when (it) {
                ' ' -> divider
                else -> transliterationChar(it) ?: transliterationChar(it.toLowerCase())?.capitalize() ?: it
            }
        }?.joinToString("")
    }

    fun createAvatar(str: String, context: Context): Drawable {
        val bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawRect(
            0f,
            0f,
            500f,
            500f,
            Paint().apply {
                color = context.theme.obtainStyledAttributes(listOf(R.attr.colorAccent).toIntArray()).getColor(0, 0)
            })
        canvas.drawText(str, 250f, 320f, Paint().apply {
            color = Color.WHITE
            textSize = 200f
            textAlign = Paint.Align.CENTER
        })
        canvas.save()
        return bitmap.toDrawable(context.resources)
    }

    fun createCircleDrawable(context: Context, drawable: Drawable):Drawable{
        val size = drawable.intrinsicWidth
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint().apply {
            isAntiAlias = true
            shader = BitmapShader(drawable.toBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        }
        canvas.drawOval(0f, 0f, size.toFloat(), size.toFloat(), paint)
        canvas.save()
        return bitmap.toDrawable(context.resources)
    }
}