package com.jstudio.i_ramen

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface EatMemoryDAO {
    @Query("SELECT * FROM EatMemory")
    fun getAll(): MutableList<EatMemory>

    @Query("SELECT * FROM EatMemory Where uid = :id")
    fun selectUid(id: Int): MutableList<EatMemory>

    @Insert
    fun insertAll(users: MutableList<EatMemory>)

    @Query("UPDATE EatMemory SET ramenName = :name WHERE uid = :id")
    fun update(name: String, id: Int)

    @Query("UPDATE EatMemory SET eatDate = :name WHERE uid = :id")
    fun updateDate(name: String, id: Int)

    @Query("DELETE FROM EatMemory Where uid = :id")
    fun deleteData(id: Int)

}