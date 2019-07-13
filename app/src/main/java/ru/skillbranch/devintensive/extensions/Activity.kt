package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager


/**
 * Created by evgen.ru79@gmail.com on 12.07.2019.
 */
fun Activity.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = this.currentFocus ?: View(this)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.isKeyboardClosed() = !isKeyboardOpen()

fun Activity.isKeyboardOpen(): Boolean {
    val rect = Rect()
    val activityRootView = (this.currentFocus ?: View(this)).rootView
    activityRootView.getWindowVisibleDisplayFrame(rect)

    val defaultKeyboardDP = 100
    val estimatedKeyboardDP = (defaultKeyboardDP + 48).toFloat()

    val estimatedKeyboardHeight = TypedValue
        .applyDimension(TypedValue.COMPLEX_UNIT_DIP, estimatedKeyboardDP, activityRootView.resources.displayMetrics)
        .toInt()

    val heightDiff = activityRootView.height - (rect.bottom - rect.top)

    return heightDiff >= estimatedKeyboardHeight
}