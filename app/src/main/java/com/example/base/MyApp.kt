package com.example.base

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        // realm -------------------------------------------
        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
            .name("TodoList.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()

        Realm.setDefaultConfiguration(configuration)
        //----------------------------------------------------
    }
}