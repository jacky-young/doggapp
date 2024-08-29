/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.nowinandroid.core.designsystem.component.media

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.AssetDataSource
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.FileDataSource
import androidx.media3.datasource.FileDataSource.FileDataSourceException
import androidx.media3.datasource.RawResourceDataSource

@OptIn(UnstableApi::class)
internal fun VideoPlayerMediaItem.toUri(
    context: Context,
): Uri = when (this) {
    is VideoPlayerMediaItem.RawResourceMediaItem -> {
        RawResourceDataSource.buildRawResourceUri(resourceId)
    }

    is VideoPlayerMediaItem.AssetFileMediaItem -> {
        val dataSpec = DataSpec(Uri.parse("asset:///$assetPath"))
        val assetDataSource = AssetDataSource(context)
        try {
            assetDataSource.open(dataSpec)
        } catch (e: AssetDataSource.AssetDataSourceException) {
            e.printStackTrace()
        }

        assetDataSource.uri ?: Uri.EMPTY
    }

    is VideoPlayerMediaItem.NetworkMediaItem -> {
        Uri.parse(url)
    }

    is VideoPlayerMediaItem.StorageMediaItem -> {
        val dataSpec = DataSpec(storageUri)
        val fileDataSource = FileDataSource()
        try {
            fileDataSource.open(dataSpec)
        } catch (e: FileDataSourceException) {
            e.printStackTrace()
        }

        fileDataSource.uri ?: Uri.EMPTY
    }
}