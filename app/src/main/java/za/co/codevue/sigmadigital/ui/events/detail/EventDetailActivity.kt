package za.co.codevue.sigmadigital.ui.events.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import za.co.codevue.sigmadigital.databinding.ActivityEventDetailBinding

@AndroidEntryPoint
class EventDetailActivity : AppCompatActivity() {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityEventDetailBinding.inflate(layoutInflater)
    }
    private val viewModel: EventDetailViewModel by viewModels()

    private var eventId: String? = null
    private var player: ExoPlayer? = null

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // get eventId
        intent.extras?.also {
            eventId = EventDetailActivityArgs.fromBundle(it).eventId
        } ?: kotlin.run {
            // exit if eventId is not set
            // TODO show error?
            finish()
        }
        // init toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViewModel()
    }

    private fun initViewModel() = with(viewModel) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                uiState.collectLatest {
                    when (it) {
                        is EventDetailUiState.Success -> {
                            playVideo(it.event.videoUrl)
                            // update UI
                            binding.event = it.event
                        }
                        is EventDetailUiState.Error -> {
                            Timber.e(it.message)
                            // TODO show error
                        }
                        else -> {
                            // ignore Idle state
                        }
                    }
                }
            }
        }
        eventId?.also { getEvent(eventId = it) }
    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(this)
            .build()
            .also { exoPlayer ->
                binding.videoPlayer.player = exoPlayer
                // restore player state
                viewModel.playerState?.also { state ->
                    exoPlayer.apply {
                        playWhenReady = state.playWhenReady
                        seekTo(state.currentMediaItemIndex, state.contentPlaybackPosition)
                        prepare()
                    }
                }
            }
    }

    private fun playVideo(videoUrl: String) {
        if (videoUrl.isNotBlank()) {
            player?.apply {
                setMediaItem(MediaItem.fromUri(videoUrl))
                playWhenReady = true
                prepare()
            }
        }
    }

    private fun releasePlayer() {
        player?.let {
            // save player state
            viewModel.playerState = PlayerState(
                currentMediaItemIndex = it.currentMediaItemIndex,
                contentPlaybackPosition = it.contentPosition,
                playWhenReady = it.playWhenReady
            )
            it.release()
        }
        player = null
    }

    override fun onResume() {
        super.onResume()
        if (player == null) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    // handle back button press
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}