package com.example.realmdbapplication.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class NotesId(
    @PrimaryKey
    var id: Int?=null,
    var title: String?=null,
    var description: String?=null,
    var date: String?=null
) : RealmObject()