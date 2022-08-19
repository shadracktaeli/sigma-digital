package za.co.codevue.sigmadigital.ui.events.detail

data class PlayerState(
    val currentMediaItemIndex: Int = 0,
    val contentPlaybackPosition: Long = 0L,
    val playWhenReady: Boolean = true
)