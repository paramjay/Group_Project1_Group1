package com.example.group_project1_group1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class NewConnectionAdaptor(options: FirebaseRecyclerOptions<UserModel>) : FirebaseRecyclerAdapter<UserModel,
        NewConnectionAdaptor.MyViewHolder>(options)  {

    interface OnButtonClickListener {
        fun onButtonClick(userModel: UserModel)
    }
    private var onButtonClickListener: OnButtonClickListener? = null

    fun setOnButtonClickListener(listener: OnButtonClickListener) {
        this.onButtonClickListener = listener
    }
//    var onItemClick: ((position: Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_connections, parent, false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: UserModel) {
        val storRef: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(model.image)
        Glide.with(holder.userProfile.context).load(storRef).into(holder.userProfile)

        holder.NewConUserName.text = "${model.username}"

//        holder.tViewtitle.text = "${model.title}"

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val NewConUserName: TextView = itemView.findViewById(R.id.NewConUserName)
        val userProfile: ImageView = itemView.findViewById(R.id.userProfile)

        val NewConConnect: Button = itemView.findViewById(R.id.NewConConnect)

        init {
            NewConConnect.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    NewConConnect.text="Connected"
                    val item = getItem(position)
                    onButtonClickListener?.onButtonClick(item)
                }
            }
        }
    }
}