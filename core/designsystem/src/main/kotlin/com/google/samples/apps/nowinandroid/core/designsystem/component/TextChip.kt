/*
 * Copyright 2020 The Android Open Source Project
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

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.google.samples.apps.nowinandroid.core.designsystem.theme.NiaTheme

@Composable
fun TextChip(
    name: String,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.extraSmall
) {
    val backgroundColor by animateColorAsState(
        MaterialTheme.colorScheme.background.copy(
            alpha = 0.6f
        ),
        label = "background color"
    )

    val textColor by animateColorAsState(
        MaterialTheme.colorScheme.inverseSurface,
        label = "text color"
    )

    Box(
        modifier = modifier
            .background(backgroundColor, shape),
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            maxLines = 1,
            modifier = Modifier.padding(
                horizontal = 8.dp,
                vertical = 2.dp
            )
        )
    }
}

@ThemePreviews
@Composable
private fun TextChipPreview() {
    NiaTheme {
        TextChip(name = "Demo", Modifier.padding(4.dp))
    }
}
