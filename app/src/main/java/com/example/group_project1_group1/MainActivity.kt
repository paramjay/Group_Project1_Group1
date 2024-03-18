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

        RviewMain.adapter = adapter

        val buttonNavigate: Button = findViewById(R.id.GoToCandidate);
        buttonNavigate.setOnClickListener {
            val intent = Intent(this, Candidate::class.java)
            startActivity(intent)
        }
    }
    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }
}