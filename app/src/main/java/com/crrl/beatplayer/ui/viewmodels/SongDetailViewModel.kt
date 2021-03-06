/*
 * Copyright (c) 2020. Carlos René Ramos López. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.crrl.beatplayer.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crrl.beatplayer.R
import com.crrl.beatplayer.databinding.FragmentLyricBinding
import com.crrl.beatplayer.databinding.FragmentSongDetailBinding
import com.crrl.beatplayer.models.Song
import com.crrl.beatplayer.ui.activities.MainActivity
import com.crrl.beatplayer.utils.LyricsExtractor


class SongDetailViewModel(private val mainActivity: MainActivity?) : ViewModel() {

    private val lyrics: MutableLiveData<String> = MutableLiveData()
    var binding: FragmentSongDetailBinding? = null
    var bindingLB: FragmentLyricBinding? = null

    fun update(raw: ByteArray?) {
        mainActivity?.viewModel!!.update(raw)
    }

    fun updateTime(newTime: Int) {
        mainActivity?.viewModel!!.update(newTime)
    }

    private fun loadLyrics(song: Song) {
        Thread {
            val lyric = LyricsExtractor.getLyric(song)
                ?: mainActivity!!.getString(R.string.no_lyrics)
            if (mainActivity!!.viewModel.getCurrentSong().value!!.id == song.id && lyric != lyrics.value) {
                lyrics.postValue(lyric)
            }
        }.start()
    }

    fun getTime() = mainActivity?.viewModel!!.getTime()

    fun getRawData() = mainActivity?.viewModel!!.getRawData()

    fun getCurrentData() = mainActivity?.viewModel!!.getCurrentSong()

    fun getLyrics(song: Song): LiveData<String> {
        loadLyrics(song)
        return lyrics
    }
}