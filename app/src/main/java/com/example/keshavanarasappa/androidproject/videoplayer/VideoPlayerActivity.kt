package com.example.keshavanarasappa.androidproject.videoplayer

import android.app.PictureInPictureParams
import android.content.ComponentName
import android.content.Intent
import android.content.res.Configuration
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaSessionCompat
import android.util.Rational
import android.view.Menu
import android.view.MenuItem
import com.example.keshavanarasappa.androidproject.R
import com.example.keshavanarasappa.androidproject.common.BaseActivity
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
import kotlinx.android.synthetic.main.activity_video_player.*

class VideoPlayerActivity : BaseActivity() {
    private var didLostBackStack = false
    private lateinit var menu: Menu

    private val mediaSession: MediaSessionCompat by lazy { createMediaSession() }
    private val mediaSessionConnector: MediaSessionConnector by lazy {
        createMediaSessionConnector()
    }
    private val playerState by lazy { PlayerState() }
    private lateinit var playerHolder: PlayerHolder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
        volumeControlStream = AudioManager.STREAM_MUSIC
        createMediaSession()
        createPlayer()
    }

    override fun onStart() {
        super.onStart()
        startPlayer()
        activateMediaSession()
    }

    override fun onStop() {
        super.onStop()
        stopPlayer()
        deactivateMediaSession()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
        releaseMediaSession()
    }

    // MediaSession related functions.
    private fun createMediaSession() = MediaSessionCompat(this, packageName)

    private fun createMediaSessionConnector() = MediaSessionConnector(mediaSession).apply {
                // If QueueNavigator isn't set, then mediaSessionConnector will not handle following
                // MediaSession actions (and they won't show up in the minimized PIP activity):
                // [ACTION_SKIP_PREVIOUS], [ACTION_SKIP_NEXT], [ACTION_SKIP_TO_QUEUE_ITEM]
                setQueueNavigator(object : TimelineQueueNavigator(mediaSession) {
                    override fun getMediaDescription(windowIndex: Int): MediaDescriptionCompat {
                        return MediaCatalog[windowIndex]
                    }
                })
            }

    private fun activateMediaSession() {
        // Note: do not pass a null to the 3rd param below, it will cause a NullPointerException.
        // To pass Kotlin arguments to Java varargs, use the Kotlin spread operator `*`.
        mediaSessionConnector.setPlayer(playerHolder.audioFocusPlayer, null)
        mediaSession.isActive = true
    }

    private fun deactivateMediaSession() {
        mediaSessionConnector.setPlayer(null, null)
        mediaSession.isActive = false
    }

    private fun releaseMediaSession() {
        mediaSession.release()
    }

    private fun createPlayer() {
        playerHolder = PlayerHolder(this, playerState, exoPlayerView)
    }

    private fun startPlayer() {
        playerHolder.start()
    }

    private fun stopPlayer() {
        playerHolder.stop()
    }

    private fun releasePlayer() {
        playerHolder.release()
    }

    // Picture in Picture related functions.
    private fun enterPIPMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            didLostBackStack = true
            enterPictureInPictureMode(
                    with(PictureInPictureParams.Builder()) {
                        val width = 26
                        val height = 19
                        setAspectRatio(Rational(width, height))
                        build()
                    })
        }
    }
    override fun onUserLeaveHint() {
        enterPIPMode()
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean, newConfig: Configuration?) {
        exoPlayerView.useController = !isInPictureInPictureMode
    }

    override fun finish() {
        if (didLostBackStack) {
            finishAndRemoveTask()
            startActivity(Intent.makeRestartActivityTask(ComponentName(this, VideoPlayerActivity::class.java)))
        } else {
            super.finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.pip, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_pip) {
            enterPIPMode()
        }
        return super.onOptionsItemSelected(item)
    }
}
