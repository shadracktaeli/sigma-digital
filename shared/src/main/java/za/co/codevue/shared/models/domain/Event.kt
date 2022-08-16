package za.co.codevue.shared.models.domain

data class Event(
    val id: String,
    val date: String,
    val imageUrl: String,
    val subtitle: String,
    val title: String,
    val videoUrl: String
)