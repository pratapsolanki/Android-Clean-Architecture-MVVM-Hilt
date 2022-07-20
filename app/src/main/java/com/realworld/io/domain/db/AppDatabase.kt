package com.example.dagger_hilt.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dagger_hilt.dao.AppDao
import com.realworld.io.model.ArticleModel


@Database(entities = [ArticleModel::class],version = 1 , exportSchema = false)
abstract class AppDatabase :RoomDatabase() {

    abstract fun getDAO(): AppDao

    companion object{
        private var dbINSTANCE :AppDatabase?= null

        fun getAppDb(context: Context) : RoomDatabase{
            if (dbINSTANCE==null){
                dbINSTANCE = Room.databaseBuilder(
                    context.applicationContext , AppDatabase::class.java,"MyDB"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return dbINSTANCE!!
        }
    }

}