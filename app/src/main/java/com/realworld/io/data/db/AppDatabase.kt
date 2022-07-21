package com.example.dagger_hilt.db

import android.content.Context
import androidx.room.*
import com.example.dagger_hilt.dao.AppDao
import com.realworld.io.model.ArticleModel
import com.realworld.io.util.Converter


@Database(entities = [ArticleModel::class],version = 1 , exportSchema = false)
@TypeConverters(Converter::class)
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