package com.example.realmdbapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.realmdbapplication.R
import com.example.realmdbapplication.databinding.NotesLayoutBinding
import com.example.realmdbapplication.db.NotesId
import io.realm.RealmResults
import kotlinx.android.synthetic.main.notes_layout.view.*


class UserAdapter(var notesSize : RealmResults<NotesId>,var listner: NotesClickListner) : RecyclerView.Adapter<UserAdapter.UserViewholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewholder {
        var layout = DataBindingUtil.inflate<NotesLayoutBinding>(LayoutInflater.from(parent.context), R.layout.notes_layout,parent,false)
        return UserViewholder(layout,listner)
    }

    override fun onBindViewHolder(holder: UserViewholder, position: Int) {
        holder.itemView.tv_notesTitle.text = notesSize[position]?.title
        holder.itemView.tv_notesDescription.text = notesSize[position]?.description
        holder.itemView.tv_notesAddDate.text = notesSize[position]?.date
        holder.itemView.rootView.setOnClickListener {
            listner.onNotesClick(position)
        }
        holder.itemView.iv_deleteNote.setOnClickListener {
            listner.onDeleteNotesClick(position)
        }

    }

    override fun getItemCount(): Int {
        return notesSize.size
    }

    inner class UserViewholder(itemView :NotesLayoutBinding,listner: NotesClickListner) : RecyclerView.ViewHolder(itemView.root)

}

interface NotesClickListner{
    fun onNotesClick(position: Int)
    fun onDeleteNotesClick(position: Int)
}