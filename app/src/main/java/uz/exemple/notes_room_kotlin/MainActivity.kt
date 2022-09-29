package uz.exemple.notes_room_kotlin

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.exemple.notes_room_kotlin.adapter.NotesAdapter
import uz.exemple.notes_room_kotlin.database.NotesRepository
import uz.exemple.notes_room_kotlin.model.Notes
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var btn_add: FrameLayout
    lateinit var recyclerView: RecyclerView
    lateinit var et_note: EditText
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }
    fun initViews(){
        context = this
        btn_add = findViewById(R.id.btn_add)
        recyclerView = findViewById(R.id.recyclerview)
        et_note = findViewById(R.id.et_note)

        val manager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = manager
        btn_add.setOnClickListener { openAlert() }
        val adapter = NotesAdapter(this, getNotes())
        recyclerView.adapter = adapter

        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(this, recyclerView, object : RecyclerItemClickListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    // do whatever
                    Log.d("@@@", "Posittion - $position")
                    val item = getNotes()[position]
                    updateAlert(item.id, item.note)
                }

                override fun onLongItemClick(view: View, position: Int) {
                    // do whatever
                }
            })
        )
    }

    fun openAlert() {
        val editText = EditText(this)
        editText.hint = "Enter Your Note"
        editText.setHintTextColor(Color.parseColor("#C6C6C6"))
        editText.setPadding(32, 0, 16, 32)
        editText.height = 100
        val titleView = TextView(context)
        titleView.text = "New Note"
        titleView.gravity = Gravity.LEFT
        titleView.setPadding(20, 20, 20, 5)
        titleView.textSize = 20f
        titleView.setTypeface(Typeface.DEFAULT_BOLD)
        //titleView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        titleView.setTextColor(Color.parseColor("#00C6AE"))
        val dialog = AlertDialog.Builder(this)
            .setCustomTitle(titleView)
            .setView(editText)
            .setPositiveButton("Save") { dialogInterface, i ->
                val note = editText.text.toString().trim { it <= ' ' }
                if (!note.isEmpty()) {
                    saveNote(note)
                    finish()
                    overridePendingTransition(0, 0)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(Color.parseColor("#00C6AE"))
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(Color.parseColor("#00C6AE"))
        }
        dialog.show()
    }

    fun saveNote(note: String?) {
        val dateT: String = getTime()
        val repository = NotesRepository(application)
        val new_note = Notes(note, dateT)
        repository.saveNote(new_note)
        //new UserAsyncTask(repository).execute(user);
    }
    fun getNotes(): ArrayList<Notes> {
        val repository = NotesRepository(application)
        val all_notes = repository.getNotes() as ArrayList<Notes>
        val notes = ArrayList<Notes>()
        for (n in all_notes) {
            notes.add(n)
        }
        return notes
    }


    fun updateAlert(id: Int, note: String?) {
        val editText = EditText(this)
        editText.setText(note)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Update Note")
            .setView(editText)
            .setPositiveButton(
                "Update"
            ) { dialogInterface, i ->
                val note = editText.text.toString()
                updateNote(id, note)
                finish()
                overridePendingTransition(0, 0)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
            .setNegativeButton(
                "Delete"
            ) { dialog, which ->
                val repository = NotesRepository(application)
                repository.deleteNote(id)
                finish()
                overridePendingTransition(0, 0)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
            .setNeutralButton("Cancel", null)
            .create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(Color.parseColor("#00C6AE"))
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(Color.parseColor("#00C6AE"))
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
                .setTextColor(Color.parseColor("#00C6AE"))
        }
        dialog.show()
    }

    fun updateNote(id: Int, note: String?) {
        val dateT = getTime()
        val repository = NotesRepository(application)
        val new_note = Notes(id, note, dateT)
        repository.saveNote(new_note)
    }

    fun getTime(): String {
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("LLL dd")
        return simpleDateFormat.format(calendar.time).toString()
    }
}