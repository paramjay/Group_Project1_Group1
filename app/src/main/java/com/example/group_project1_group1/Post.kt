package com.example.group_project1_group1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

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


    }

}