package com.kashif.common.view

/*import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.kashif.common.AsyncImage
import com.kashif.common.domain.Breed
import com.kashif.common.view.icon.IconCustomArrowBack
import com.kashif.common.view.style.DogifyColors

@Composable
fun FullscreenImageScreen(
    picture: Breed,
    back: () -> Unit
) {
    Box(Modifier.fillMaxSize().background(color = DogifyColors.fullScreenImageBackground)) {
        AsyncImage(
            url = picture.imageUrl,
            modifier =  Modifier.fillMaxSize().clipToBounds()
        )
    }

    TopLayout(
        alignLeftContent = {
            BackButton(back)
        },
        alignRightContent = {},
    )
}

@Composable
fun TopLayout(
    alignLeftContent: @Composable () -> Unit = {},
    alignRightContent: @Composable () -> Unit = {},
) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(Modifier.align(Alignment.CenterStart)) {
            alignLeftContent()
        }
        Row(Modifier.align(Alignment.CenterEnd)) {
            alignRightContent()
        }
    }
}

@Composable
fun CircularButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    CircularButton(
        modifier = modifier,
        content = {
            Icon(imageVector, null, Modifier.size(34.dp), Color.White)
        },
        enabled = enabled,
        onClick = onClick
    )
}

@Composable
fun BackButton(onClick: () -> Unit) {
    CircularButton(
        imageVector = IconCustomArrowBack,
        onClick = onClick
    )
}
*/