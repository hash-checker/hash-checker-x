package xyz.fartem.hashcheckerx.feedback.model

data class Feedback(
    val appVersionName: String,
    val appVersionCode: Int,
    val osVersion: String,
    val manufacturer: String,
    val model: String,
    val message: String,
)
