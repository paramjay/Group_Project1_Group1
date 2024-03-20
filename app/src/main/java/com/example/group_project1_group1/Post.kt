package com.example.group_project1_group1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Post : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        val PostToHome: Button = findViewById(R.id.PostToHome);
        PostToHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val PostToCandidate: Button = findViewById(R.id.PostToCandidate);
        PostToCandidate.setOnClickListener {
            val intent = Intent(this, Candidate::class.java)
            startActivity(intent)
        }


        val editTextTitle: EditText = findViewById(R.id.editTextTitle)
        val editTextDescription: EditText = findViewById(R.id.editTextDescription)
        val addPostButton: Button = findViewById(R.id.addPostButton)

        addPostButton.setOnClickListener {
            val postTitle = editTextTitle.text.toString()
            val postDescription = editTextDescription.text.toString()
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val currentDate = Date()
            val postDate = dateFormat.format(currentDate).toString()

            val post = UserActivity(postDescription, postDate, postTitle, "Paramjay")

//            val query = FirebaseDatabase.getInstance().reference.child("activity")
            val databaseReference = FirebaseDatabase.getInstance().reference
            val postId = databaseReference.child("activity").push().key

            postId?.let {
                databaseReference.child("activity").child(it).setValue(post)
                    .addOnSuccessListener {
                        editTextTitle.text.clear()
                        editTextDescription.text.clear()
                        Toast.makeText(this, "Post added successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to add post,Try again later", Toast.LENGTH_SHORT).show()
                    }
            }
        }

    }

}