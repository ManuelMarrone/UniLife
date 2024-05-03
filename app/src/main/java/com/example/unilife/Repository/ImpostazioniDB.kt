package com.example.unilife.Repository

import com.google.firebase.firestore.FirebaseFirestore

class ImpostazioniDB {


    //FireStore
    private val fireStore = FirebaseFirestore.getInstance()
    lateinit var dbCurrentUser: DatabaseReference
    lateinit var dbCurrentUserPosts: DatabaseReference
    val dbUsers = reference.child(DATABASE_USERS_PATH)
    val dbPosts = reference.child(DATABASE_POSTS_PATH)
    val dbFollows = reference.child(DATABASE_FOLLOWS_PATH)
}