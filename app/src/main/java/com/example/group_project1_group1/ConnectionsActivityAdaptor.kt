package com.example.group_project1_group1

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.widget.ImageView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class ConnectionsActivityAdaptor(options: FirebaseRecyclerOptions<UserActivity>) : FirebaseRecyclerAdapter<UserActivity,
        ConnectionsActivityAdaptor.MyViewHolder>(options)  {

    var onItemClick: ((position: Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_post, parent, false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: UserActivity) {

        holder.textViewUserName.text = "${model.username}"

        holder.tViewtitle.text = "${model.title}"

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val textViewUserName: TextView = itemView.findViewById(R.id.textViewUserName)

        val tViewtitle: TextView = itemView.findViewById(R.id.tViewtitle)
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }
        }
    }
}