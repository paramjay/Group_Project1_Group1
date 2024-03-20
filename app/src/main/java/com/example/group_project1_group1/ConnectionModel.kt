package com.example.group_project1_group1

data class ConnectionModel(
val username: String = "",
val connectedUsers: MutableList<Int> = mutableListOf()
//val connectedUsers: List<Int> = emptyList()
)
