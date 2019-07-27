package ru.skillbranch.devintensive

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import ru.skillbranch.devintensive.repositories.PreferenciesRepository

/**
 * Created by evgen.ru79@gmail.com on 24.07.2019.
 */
class App : Application() {
    companion object {
        private var instance: App? = null
        fun applicationContext() = instance!!.applicationContext
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        PreferenciesRepository.getAppTheme().also {
            AppCompatDelegate.setDefaultNightMode(it)
        }
    }
}