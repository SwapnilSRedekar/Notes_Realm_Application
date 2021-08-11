package com.example.realmdbapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.realmdbapplication.R
import com.example.realmdbapplication.databinding.ActivityCreateNotesBinding
import com.example.realmdbapplication.db.NotesId
import io.realm.Realm
import java.lang.Exception
import java.util.*

class CreateNotesActivity : AppCompatActivity() {

    lateinit var createNotesBinding: ActivityCreateNotesBinding
    lateinit var realm :Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotesBinding = DataBindingUtil.setContentView(this,R.layout.activity_create_notes)
        realm = Realm.getDefaultInstance()

        var noteId = intent.getStringExtra("ID")
        var model = realm.where(NotesId::class.java).equalTo("id",noteId?.toInt()).findFirst()



        if (model!=null){
            createNotesBinding.etNotesTitle.setText(model?.title)
            createNotesBinding.etNotesDesc.setText(model?.description)
            createNotesBinding.fabSaveNote.setImageResource(R.drawable.ic_baseline_edit)
        }else{
            createNotesBinding.fabSaveNote.setImageResource(R.drawable.ic_baseline_check)
        }




        createNotesBinding.fabSaveNote.setOnClickListener {
            if (createNotesBinding.etNotesTitle.text.toString().isNotEmpty() && createNotesBinding.etNotesDesc.text.toString().isNotEmpty())
            {
                if (model!=null){
                    var title = createNotesBinding.etNotesTitle.text.toString()
                    var desc = createNotesBinding.etNotesDesc.text.toString()
                    updateNotesInDb(model,title,desc)

                }else{
                    addNotesToDb()
                }

                finish()
            }
            else  Toast.makeText(this,"Fill necessary fields",Toast.LENGTH_SHORT).show()

        }

    }

    fun updateNotesInDb(model: NotesId?, title: String, desc: String) {
        realm.beginTransaction()
        model?.title = title
        model?.description = desc
        model?.date = todaysDate()
        realm.copyToRealmOrUpdate(model)
        realm.commitTransaction()
    }

    fun addNotesToDb(){
        try {
            realm.beginTransaction()

                var num:Number? = realm.where(NotesId::class.java).max("id")
                var currentId :Int
                currentId = if (num==null){1} else{num.toInt()+1}

                var notesId = NotesId()
                notesId.id = currentId
                notesId.title = createNotesBinding.etNotesTitle.text.toString()
                notesId.description = createNotesBinding.etNotesDesc.text.toString()
                notesId.date = todaysDate()

                realm.copyToRealmOrUpdate(notesId)
                realm.commitTransaction()

                createNotesBinding.etNotesTitle.setText("")
                createNotesBinding.etNotesDesc.setText("")

                Toast.makeText(this,"Note added successfully",Toast.LENGTH_SHORT).show()

        }catch (e:Exception){
            Toast.makeText(this,"Error found $e",Toast.LENGTH_SHORT).show()
        }
    }

    fun todaysDate(): String {
        var now = Calendar.getInstance()
        var currentDay = now.get(Calendar.DAY_OF_MONTH)
        var currentMonth = now.get(Calendar.MONTH)
        var currentYear = now.get(Calendar.YEAR)

        var todayDate =""+currentDay+"/"+(currentMonth+1)+"/"+currentYear
        return todayDate
    }
}