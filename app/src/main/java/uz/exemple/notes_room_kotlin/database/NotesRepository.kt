package uz.exemple.notes_room_kotlin.database

import android.app.Application
import uz.exemple.notes_room_kotlin.managers.RoomManager
import uz.exemple.notes_room_kotlin.model.Notes

class NotesRepository {

    private lateinit var noteDao:NotesDao

    constructor(application: Application?) {
        val db: RoomManager = RoomManager.getDatabase(application!!)
        noteDao = db.notesDao()
    }

    fun getNotes():List<Notes>{
        return noteDao.getNotes() as List<Notes>
    }

    fun saveNote(note: Notes) {
        noteDao.saveNotes(note)
    }

    fun deleteNote(id: Int) {
        noteDao.deleteByNoteId(id)
    }
}