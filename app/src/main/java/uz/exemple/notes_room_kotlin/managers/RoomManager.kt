package uz.exemple.notes_room_kotlin.managers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.exemple.notes_room_kotlin.database.NotesDao
import uz.exemple.notes_room_kotlin.model.Notes

@Database(entities = [Notes::class], version = 1)
abstract class RoomManager: RoomDatabase() {

    abstract fun notesDao():NotesDao

    companion object {

        @Volatile
        private var INSTANCE: RoomManager? = null

        fun getDatabase(context: Context): RoomManager {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RoomManager::class.java,
                        "notes_db.sqlite"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}