package com.example.keshavanarasappa.androidproject.videoplayer

import android.net.Uri
import android.support.v4.media.MediaDescriptionCompat

/**
 * Manages a set of media metadata that is used to create a playlist for [VideoActivity].
 */

open class MediaCatalog(private val list: MutableList<MediaDescriptionCompat>) :
        List<MediaDescriptionCompat> by list {

    companion object : MediaCatalog(mutableListOf())

    init {
        // More creative commons, creative commons videos - https://www.blender.org/about/projects/
        list.add(
                with(MediaDescriptionCompat.Builder()) {
                    setDescription("MP4 loaded over HTTP")
                    setMediaId("1")
                    // License - https://peach.blender.org/download/
                    setMediaUri(Uri.parse("http://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4"))
                    setTitle("Short film Big Buck Bunny")
                    setSubtitle("Streaming video")
                    build()
                })
        list.add(
                with(MediaDescriptionCompat.Builder()) {
                    setDescription("MP4 loaded over HTTP")
                    setMediaId("2")
                    // License - https://archive.org/details/ElephantsDream
                    setMediaUri(Uri.parse("https://archive.org/download/ElephantsDream/ed_hd.mp4"))
                    setTitle("Short film Elephants Dream")
                    setSubtitle("Streaming video")
                    build()
                })
        list.add(
                with(MediaDescriptionCompat.Builder()) {
                    setDescription("MOV loaded over HTTP")
                    setMediaId("3")
                    // License - https://mango.blender.org/sharing/
                    setMediaUri(Uri.parse("http://ftp.nluug.nl/pub/graphics/blender/demo/movies/ToS/ToS-4k-1920.mov"))
                    setTitle("Short film Tears of Steel")
                    setSubtitle("Streaming audio")
                    build()
                })
    }
}