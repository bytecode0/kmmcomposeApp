package com.kashif.common.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.kashif.common.App
import com.kashif.common.view.style.DogifyColors

@Composable
fun Application() {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(
            background = DogifyColors.background,
            onBackground = DogifyColors.onBackground
        )
    ) {
        ProvideTextStyle(LocalTextStyle.current.copy(letterSpacing = 0.sp)) {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                App(platform = "Android")
            }
        }
    }
}