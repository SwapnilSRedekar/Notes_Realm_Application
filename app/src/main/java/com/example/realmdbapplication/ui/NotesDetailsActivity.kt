package com.example.realmdbapplication.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.realmdbapplication.R
import com.example.realmdbapplication.adapter.NotesClickListner
import com.example.realmdbapplication.adapter.UserAdapter
import com.example.realmdbapplication.databinding.ActivityNotesDetailsBinding
import com.example.realmdbapplication.db.NotesId
import io.realm.Realm
import io.realm.RealmResults

class NotesDetailsActivity : AppCompatActivity(),NotesClickListner {

    lateinit var activityNotesDetailsBinding : ActivityNotesDetailsBinding
    lateinit var realm: Realm
    lateinit var userAdapter: UserAdapter
    lateinit var allNotes: RealmResults<NotesId>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNotesDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_notes_details)

        realm = Realm.getDefaultInstance()
        allNotes = realm.where(NotesId::class.java).findAll()

        userAdapter = UserAdapter(allNotes,this)
        activityNotesDetailsBinding.rvNotesDetails.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        activityNotesDetailsBinding.rvNotesDetails.adapter = userAdapter
        activityNotesDetailsBinding.fabAddNotes.setOnClickListener {

            var intent = Intent(this, CreateNotesActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        allNotes = realm.where(NotesId::class.java).findAll()
        userAdapter.notifyDataSetChanged()
    }

    override fun onNotesClick(notesPosition: Int) {
        var intent = Intent(this,CreateNotesActivity::class.java)
        intent.putExtra("ID",""+ allNotes[notesPosition]?.id)
        startActivity(intent)
    }

    override fun onDeleteNotesClick(notesPosition: Int) {
        realm.beginTransaction()
        var model = realm.where(NotesId::class.java).equalTo("id",allNotes[notesPosition]?.id).findFirst()

        model?.deleteFromRealm()
        realm.commitTransaction()
        userAdapter.notifyDataSetChanged()
    }


}