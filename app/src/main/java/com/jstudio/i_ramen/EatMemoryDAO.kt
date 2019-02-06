package com.jstudio.i_ramen

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface EatMemoryDAO {
    @Query("SELECT * FROM EatMemory")
    fun getAll(): MutableList<EatMemory>

    @Insert
    fun insertAll(users: MutableList<EatMemory>)

}