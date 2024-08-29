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

package com.google.samples.apps.nowinandroid.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.google.samples.apps.nowinandroid.core.designsystem.theme.NiaTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun BannerGames(
    pagerState: PagerState,
    items: PersistentList<String>,
    navigateToGameDetails: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(Color.Transparent)
    ) {
        val horizontalPadding = 30.dp
        HorizontalPager (
            state = pagerState,
            contentPadding = PaddingValues(
                horizontal = horizontalPadding,
                vertical = 16.dp,
            ),
            pageSpacing = 24.dp,
            pageSize = onePagesPerViewport,
        ) { page ->
            val bannerGame = items[page]
            BannerGamesCarouselItem(
                gameTitle = bannerGame,
                gameImageUrl = "",
                priceOrDownloadLabel = bannerGame,
                modifier = Modifier
                    .clickable {
                        navigateToGameDetails(bannerGame)
                    }
            )
        }
    }
}

private val FEATURED_BANNER_IMAGE_WIDTH_DP = 640.dp
private val FEATURED_BANNER_IMAGE_HEIGHT_DP = 160.dp
private val onePagesPerViewport = object : PageSize {
    override fun Density.calculateMainAxisPageSize(
        availableSpace: Int,
        pageSpacing: Int
    ): Int {
        return (availableSpace - 1 * pageSpacing) / 1
    }
}

@Composable
private fun BannerGamesCarouselItem(
    gameTitle: String,
    gameImageUrl: String,
    modifier: Modifier = Modifier,
    priceOrDownloadLabel: String? = null,
) {
    Column(modifier) {
        Box(
            Modifier
                .size(
                    width = FEATURED_BANNER_IMAGE_WIDTH_DP,
                    height = FEATURED_BANNER_IMAGE_HEIGHT_DP,
                )
                .align(Alignment.CenterHorizontally)
        ) {
            DynamicAsyncImage(
                imageUrl = gameImageUrl,
                contentDescription = gameTitle,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.small)
            )

            if (priceOrDownloadLabel != null) {
                TextChip(
                    name = priceOrDownloadLabel,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(4.dp),
                )
            }
        }
    }
}

@ThemePreviews
@Composable
fun BannerGameCarouselItemPreview() {
    NiaTheme {
        BannerGamesCarouselItem(
            gameTitle = "Test",
            gameImageUrl = "",
            priceOrDownloadLabel = "$ 0.99",
        )
    }
}

@ThemePreviews
@Composable
fun BannerGamesPreview() {
    NiaTheme {
        val sillyData: PersistentList<String> = persistentListOf("$ 0.99", "Download","$ 0.99", "Download","$ 0.99");
        val pagerState = rememberPagerState { sillyData.size }
        BannerGames(
            pagerState = pagerState,
            items = sillyData,
            navigateToGameDetails = {}
        )
    }
}