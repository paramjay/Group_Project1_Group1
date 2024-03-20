package com.example.group_project1_group1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class NewConnections : AppCompatActivity() {

    private var adapter: NewConnectionAdaptor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_connections)

        val query = FirebaseDatabase.getInstance().reference.child("user")
        val options = FirebaseRecyclerOptions.Builder<UserModel>()
            .setQuery(query, UserModel::class.java)
            .build()

        val rViewNewConnections: RecyclerView = findViewById(R.id.rViewNewConnections)
        rViewNewConnections.layoutManager = LinearLayoutManager(this)
        adapter = NewConnectionAdaptor(options)

        // Overriding Click event of button
        adapter!!.setOnButtonClickListener(object : NewConnectionAdaptor.OnButtonClickListener {
            override fun onButtonClick(userModel: UserModel) {
                //for assignment we have taken an already Log In User
                val LogedInUserId = 1
                val LogedInUsername = "Paramjay"
                val NewConnectionUsername = "${userModel.username}" // getting from click
                var NewConnectionUserID = 0 // initializing 0 as no entry like 0 i database
                val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
                val usersRef = databaseReference.child("users")
                val connectionsRef = databaseReference.child("connections")
                val query: Query = usersRef.orderByChild("username").equalTo(NewConnectionUsername)

                // getting new user id from database and setting in the variable
                query.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (snapshot in dataSnapshot.children) {

                                Log.i("NewConnectionUserID", snapshot.key!!)
                                NewConnectionUserID = snapshot.key!!.toString()?.toInt() ?: 0
                            }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.i("NewConnection", "Error fetching New User Info: ${databaseError.message}")
                    }
                })
                // when getting ID of the user if condition will be true
                if(NewConnectionUserID>0){

                    //Updating connnection table for newUser
                    val query2: Query = connectionsRef.orderByChild("username").equalTo(NewConnectionUsername)
                    query2.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (connectionSnapshot in dataSnapshot.children) {
                                    var NewConnectionModel = connectionSnapshot.getValue(ConnectionModel::class.java)
                                    if (NewConnectionModel != null) {
                                        NewConnectionModel.connectedUsers.add(LogedInUserId)
                                    }
                                    connectionsRef.child(NewConnectionUserID.toString()).setValue(NewConnectionModel)
                                        .addOnSuccessListener {
                                            Log.i("NewConnection", "New connection added successfully to connection for New User")
                                        }
                                        .addOnFailureListener { e ->
                                            Log.i("NewConnection", "Error adding new connection to connection, error: $e")
                                        }
                                }
                            }
                        }
                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.i("NewConnection", "Error fetching connections: ${databaseError.message}")
                        }
                    })

                    //Updating connnection table for LogedIn User
                    val query3: Query = connectionsRef.orderByChild("username").equalTo(LogedInUsername)
                    query3.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (connectionSnapshot in dataSnapshot.children) {
                                    var LogedInUserModel = connectionSnapshot.getValue(ConnectionModel::class.java)
                                    if (LogedInUserModel != null) {
                                        LogedInUserModel.connectedUsers.add(NewConnectionUserID)
                                    }
                                    connectionsRef.child(LogedInUserId.toString()).setValue(LogedInUserModel)
                                        .addOnSuccessListener {
                                            Log.i("NewConnection", "New connection added successfully to connection for logedIn User")
                                        }
                                        .addOnFailureListener { e ->
                                            Log.i("NewConnection", "Error adding new connection to connection, error: $e")
                                        }
                                }
                            }
                        }
                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.i("NewConnection", "Error fetching connections: ${databaseError.message}")
                        }
                    })

                    // in the end Toast message for user that connection is made
                    Toast.makeText(applicationContext, "Connected to user - ${userModel.username}", Toast.LENGTH_SHORT).show()
                }else{
                    // in the end Toast message for user that connection is made
                    Toast.makeText(applicationContext, "Enable to connect with user - ${userModel.username}", Toast.LENGTH_SHORT).show()
                }

            }
        })
        rViewNewConnections.adapter = adapter
    }
    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

}