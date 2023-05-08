package com.example.najahni.models

data class UserWithMessage(
    val user: User,
    val messages: List<Message>
):java.io.Serializable
