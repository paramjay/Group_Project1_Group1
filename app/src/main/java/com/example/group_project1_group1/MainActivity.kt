package com.example.group_project1_group1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private var adapter: ConnectionsActivityAdaptor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val query = FirebaseDatabase.getInstance().reference.child("activity")
        val options = FirebaseRecyclerOptions.Builder<UserActivity>()
            .setQuery(query, UserActivity::class.java)
            .build()

        val RviewMain: RecyclerView = findViewById(R.id.RviewMain)
        RviewMain.layoutManager = LinearLayoutManager(this)
        adapter = ConnectionsActivityAdaptor(options)

        adapter!!.onItemClick = { position ->

            val userActivity = adapter!!.getItem(position)

            val intent = Intent(this, Details::class.java)
            intent.putExtra("username", userActivity.username)
            intent.putExtra("title", userActivity.title)
            intent.putExtra("description", userActivity.description)
            intent.putExtra("date", userActivity.date)
            startActivity(intent)
        }

        RviewMain.adapter = adapter

        val GoToCandidate: Button = findViewById(R.id.GoToCandidate);
        GoToCandidate.setOnClickListener {
            val intent = Intent(this, Candidate::class.java)
            startActivity(intent)
        }
//        val homeButton: Button = findViewById(R.id.homeButton);
//        homeButton.setOnClickListener {
//            val intent = Intent(this, Candidate::class.java)
//            startActivity(intent)
//        }
        val postButton: Button = findViewById(R.id.postButton);
        postButton.setOnClickListener {
            val intent = Intent(this, Post::class.java)
            startActivity(intent)
        }
    }
    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }
}

