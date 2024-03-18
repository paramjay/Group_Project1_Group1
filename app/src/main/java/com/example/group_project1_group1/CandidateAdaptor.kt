package com.example.group_project1_group1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CandidateAdaptor(options: FirebaseRecyclerOptions<UserActivity>) : FirebaseRecyclerAdapter<UserActivity,
        CandidateAdaptor.MyViewHolder>(options)  {

    var onItemClick: ((position: Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_post, parent, false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: UserActivity) {
        val storRef: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(model.image)
        Glide.with(holder.posted_image.context).load(storRef).into(holder.posted_image)

        holder.textViewUserName.text = "${model.username}"

        holder.tViewtitle.text = "${model.title}"

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val textViewUserName: TextView = itemView.findViewById(R.id.textViewUserName)

        val tViewtitle: TextView = itemView.findViewById(R.id.tViewtitle)
        val posted_image: ImageView = itemView.findViewById(R.id.posted_image)
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }
        }
    }
}