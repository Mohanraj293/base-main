package com.example.base.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Notes(
    @PrimaryKey
    var id:String?=null,
    var title: String? =null,
    var description:String?=null
):RealmObject()