package com.jstudio.i_ramen

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class EatMemory {

    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

    @ColumnInfo(name = "ramenName")
    var ramenName: String? = null

    @ColumnInfo(name = "eatDate")
    var eatDate: String? = null


}