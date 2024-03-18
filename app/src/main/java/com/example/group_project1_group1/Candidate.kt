package com.example.group_project1_group1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class Candidate : AppCompatActivity() {

    private var adapter: CandidateAdaptor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.candidate)

        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("activity")

        val query: Query = databaseReference.orderByChild("username").equalTo("paramjay")
        val options = FirebaseRecyclerOptions.Builder<UserActivity>()
            .setQuery(query, UserActivity::class.java)
            .build()

        val RecyclerViewMyActivity: RecyclerView = findViewById(R.id.RecyclerViewMyActivity)
        RecyclerViewMyActivity.layoutManager = LinearLayoutManager(this)
        adapter = CandidateAdaptor(options)

        RecyclerViewMyActivity.adapter = adapter

    }
    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }
}