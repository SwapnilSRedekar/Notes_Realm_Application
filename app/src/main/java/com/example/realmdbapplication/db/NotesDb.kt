package com.example.realmdbapplication.db

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class NotesDb : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        var config = RealmConfiguration.Builder().allowQueriesOnUiThread(true)
            .deleteRealmIfMigrationNeeded().name("NoteAppDB").build()

        Realm.setDefaultConfiguration(config)
    }
}