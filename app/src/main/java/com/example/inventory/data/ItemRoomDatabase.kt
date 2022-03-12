package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemRoomDatabase: RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object{
        @Volatile
        private var INSTANCE: ItemRoomDatabase? = null

        fun getDatabase(context: Context): ItemRoomDatabase{

            //getDatabase() 내에서 INSTANCE 변수를 반환하거나 INSTANCE가 null이면 synchronized{} 블록 내에서 초기화합니다.
            // elvis 연산자(?:)를 사용하면 됩니다. 함수 블록 내에서 잠그려는 컴패니언 객체를 this에 전달합니다. 다음 단계에서 오류를 수정합니다.
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemRoomDatabase::class.java,
                    "item_database"
                ) .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}