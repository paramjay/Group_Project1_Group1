package com.example.group_project1_group1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Details : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail)
        val intent = intent

        val username: String? = intent.getStringExtra("username")

        val title: String? = intent.getStringExtra("title")

        val image: String? = intent.getStringExtra("image")

        val textViewUserName: TextView = findViewById(R.id.textViewUserName)
        val textViewTitle: TextView = findViewById(R.id.textViewTitle)
        val detailPostImage: ImageView = findViewById(R.id.detailPostImage)
        val DetailsProfilePhoto: ImageView = findViewById(R.id.DetailsProfilePhoto)

        if (!image.isNullOrEmpty()) {
            val storRef: StorageReference =
                FirebaseStorage.getInstance().getReferenceFromUrl(image.toString())
            Glide.with(detailPostImage.context).load(storRef).into(detailPostImage)
        }

        textViewTitle.text=title
        textViewUserName.text=username


        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("user")
        val query: Query = databaseReference.orderByChild("username").equalTo(username)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val UserImage = snapshot.child("image").getValue(String::class.java)
                    if (!UserImage.isNullOrEmpty()) {
                        val storRef: StorageReference =
                            FirebaseStorage.getInstance().getReferenceFromUrl(UserImage.toString())
                        Glide.with(DetailsProfilePhoto.context).load(storRef).into(DetailsProfilePhoto)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
                Log.i("Error reading data", databaseError.toException().toString())
            }
        })

        val DetailsConnectButton: Button = findViewById(R.id.DetailsConnectButton);
        DetailsConnectButton.setOnClickListener {
            if(DetailsConnectButton.text.equals("Connected")){
                DetailsConnectButton.text="Connect"
            }else{
                DetailsConnectButton.text="Connected"
            }
        }


    }
}