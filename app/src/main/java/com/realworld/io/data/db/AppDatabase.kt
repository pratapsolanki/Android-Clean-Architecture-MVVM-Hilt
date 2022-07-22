package com.example.dagger_hilt.db

import android.content.Context
import androidx.room.*
import com.realworld.io.data.local.AppDao
import com.realworld.io.domain.model.ArticleX
import com.realworld.io.domain.model.Author
import com.realworld.io.util.Converter
import com.realworld.io.util.ListConverter
import com.realworld.io.util.ObjectConverter


@Database(entities = [ArticleX::class],version = 1 , exportSchema = false)
@TypeConverters(Converter::class, ObjectConverter::class,ListConverter::class)
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