package com.jstudio.i_ramen

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(EatMemory::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eatMemoryDAO(): EatMemoryDAO
}