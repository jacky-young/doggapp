/*
 * Copyright 2021 The Android Open Source Project
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

package com.google.samples.apps.nowinandroid.feature.interests

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.MimeTypes
import com.google.samples.apps.nowinandroid.core.designsystem.component.BannerGames
import com.google.samples.apps.nowinandroid.core.designsystem.component.NiaBackground
import com.google.samples.apps.nowinandroid.core.designsystem.component.NiaLoadingWheel
import com.google.samples.apps.nowinandroid.core.designsystem.component.media.VideoPlayer
import com.google.samples.apps.nowinandroid.core.designsystem.component.media.VideoPlayerMediaItem
import com.google.samples.apps.nowinandroid.core.designsystem.theme.NiaTheme
import com.google.samples.apps.nowinandroid.core.model.data.FollowableTopic
import com.google.samples.apps.nowinandroid.core.ui.DevicePreviews
import com.google.samples.apps.nowinandroid.core.ui.FollowableTopicPreviewParameterProvider
import com.google.samples.apps.nowinandroid.core.ui.TrackScreenViewEvent
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun InterestsRoute(
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    highlightSelectedTopic: Boolean = false,
    viewModel: InterestsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    InterestsScreen(
        uiState = uiState,
        followTopic = viewModel::followTopic,
        onTopicClick = {
            viewModel.onTopicClick(it)
            onTopicClick(it)
        },
        highlightSelectedTopic = highlightSelectedTopic,
        modifier = modifier,
    )
}

@Composable
internal fun InterestsScreen(
    uiState: InterestsUiState,
    followTopic: (String, Boolean) -> Unit,
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    highlightSelectedTopic: Boolean = false,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val sillyData: PersistentList<String> = persistentListOf("$ 0.99", "Download","$ 0.99", "Download","$ 0.99");
        val pagerState = rememberPagerState { sillyData.size }
        BannerGames(
            modifier = modifier,
            pagerState = pagerState,
            items = sillyData,
            navigateToGameDetails = {}
        )

        val samplePlayList = listOf(
            VideoPlayerMediaItem.NetworkMediaItem(
                url = "https://storage.googleapis.com/downloads.webmproject.org/av1/exoplayer/bbb-av1-480p.mp4",
                mediaMetadata = MediaMetadata.Builder().setTitle("Clear MP4: Dizzy").build(),
                mimeType = MimeTypes.VIDEO_MP4,
            ),
        )
        Spacer(modifier = Modifier.height(12.dp))
        VideoPlayer(
            mediaItems = samplePlayList,
            modifier = modifier,
        )
        Spacer(modifier = Modifier.height(12.dp))
        when (uiState) {
            InterestsUiState.Loading ->
                NiaLoadingWheel(
                    modifier = modifier,
                    contentDesc = stringResource(id = R.string.feature_interests_loading),
                )

            is InterestsUiState.Interests ->
                TopicsTabContent(
                    topics = uiState.topics,
                    onTopicClick = onTopicClick,
                    onFollowButtonClick = followTopic,
                    selectedTopicId = uiState.selectedTopicId,
                    highlightSelectedTopic = highlightSelectedTopic,
                    modifier = modifier,
                )

            is InterestsUiState.Empty -> InterestsEmptyScreen()
        }
    }
    TrackScreenViewEvent(screenName = "Interests")
}

@Composable
private fun InterestsEmptyScreen() {
    Text(text = stringResource(id = R.string.feature_interests_empty_header))
}

@DevicePreviews
@Composable
fun InterestsScreenPopulated(
    @PreviewParameter(FollowableTopicPreviewParameterProvider::class)
    followableTopics: List<FollowableTopic>,
) {
    NiaTheme {
        NiaBackground {
            InterestsScreen(
                uiState = InterestsUiState.Interests(
                    selectedTopicId = null,
                    topics = followableTopics,
                ),
                followTopic = { _, _ -> },
                onTopicClick = {},
            )
        }
    }
}

@DevicePreviews
@Composable
fun InterestsScreenLoading() {
    NiaTheme {
        NiaBackground {
            InterestsScreen(
                uiState = InterestsUiState.Loading,
                followTopic = { _, _ -> },
                onTopicClick = {},
            )
        }
    }
}

@DevicePreviews
@Composable
fun InterestsScreenEmpty() {
    NiaTheme {
        NiaBackground {
            InterestsScreen(
                uiState = InterestsUiState.Empty,
                followTopic = { _, _ -> },
                onTopicClick = {},
            )
        }
    }
}
