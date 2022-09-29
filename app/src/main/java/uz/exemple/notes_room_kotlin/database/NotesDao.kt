package uz.exemple.notes_room_kotlin.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.exemple.notes_room_kotlin.model.Notes

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNotes(note: Notes?): Long

    @Query("select * from notes_table")
    fun getNotes(): List<Notes?>?

    @Query("delete from notes_table where id = :noteId")
    fun deleteByNoteId(noteId: Int)
}