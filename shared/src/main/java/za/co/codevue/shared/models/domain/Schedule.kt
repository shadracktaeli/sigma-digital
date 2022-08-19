package za.co.codevue.shared.models.domain

import java.util.*

data class Schedule(
    val id: String,
    val date: Date,
    val imageUrl: String,
    val subtitle: String,
    val title: String
)