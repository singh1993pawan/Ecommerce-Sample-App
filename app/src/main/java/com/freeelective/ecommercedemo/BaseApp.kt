package com.freeelective.ecommercedemo

import android.app.Application
import java.io.File


class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()
    }
}