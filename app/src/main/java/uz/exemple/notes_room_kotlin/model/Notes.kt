package uz.exemple.notes_room_kotlin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
open class Notes {

    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "note")
    var note: String? = null

    @ColumnInfo(name = "date")
    var date: String? = null

    constructor() {}
    @Ignore
    constructor(note: String?, date: String?) {
        id = 0
        this.note = note
        this.date = date
    }

    @Ignore
    constructor(id: Int, note: String?, date: String?) {
        this.id = id
        this.note = note
        this.date = date
    }
}