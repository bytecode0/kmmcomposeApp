package com.kashif.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kashif.common.domain.Breed
import com.kashif.common.domain.ScalableState
import com.kashif.common.view.BreedsUIState
import com.kashif.common.view.MainViewModel
import com.kashif.common.view.NavigationStack
import com.kashif.common.view.icon.IconCustomArrowBack
import com.kashif.common.view.icon.IconMenu
import com.kashif.common.view.style.DogifyColors
import com.seiko.imageloader.ImageRequestState
import com.seiko.imageloader.rememberAsyncImagePainter
import kotlinx.coroutines.delay

@Composable
internal fun App(platform: String) {
    val viewModel: MainViewModel = remember { MainViewModel() }
    val breedsStateFlow by viewModel.breeds.collectAsState()
    var text by remember { mutableStateOf("Hello, World!") }
    var showImage by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            AnimatedVisibility(showImage) {
                MainCommon(breedsStateFlow)
            }
        }
    }
}

@Composable
internal fun MainCommon(breedsStateFlow: BreedsUIState<Any>) {
    val navigationStack: NavigationStack<Page> = remember { NavigationStack(GalleryPage()) }

    when (val page = navigationStack.lastWithIndex().value) {
        is GalleryPage -> GalleryScreen(
            breedsStateFlow,
            onClickPreviewPicture = { breed ->
                navigationStack.push(FullScreenPage(breed))
            }
        )
        is FullScreenPage -> FullscreenImageScreen(
            breed = page.breed,
            back = { navigationStack.back() }
        )
    }
}

@Composable
internal fun AsyncImage(url: String, modifier: Modifier) {
    val painter = rememberAsyncImagePainter(url = url)
    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier,
    )
    when (val requestState = painter.requestState) {
        ImageRequestState.Loading -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is ImageRequestState.Failure -> {
            Text(requestState.error.message ?: "Error")
        }
        ImageRequestState.Success -> Unit
    }
}

@Composable
internal fun GalleryScreen(
    breedsStateFlow: BreedsUIState<Any>,
    onClickPreviewPicture: (breed: Breed) -> Unit
) {
    Box(Modifier
        .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                TopLayout(
                    alignLeftContent = {
                        Text("🐶 Dogify", style = TextStyle(color = Color.Black, fontSize = 24.sp))
                    },
                    alignRightContent = {
                        Box(modifier =
                        Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(DogifyColors.uiLightBlack)
                            .run {
                                clickable {
                                    //TODO: Add navigation to settings
                                }
                            },
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(IconMenu, null, Modifier.size(34.dp), Color.White)
                        }
                    },
                )
            }
            Row {
                when (breedsStateFlow) {
                    is BreedsUIState.Loading -> LoadingView()
                    is BreedsUIState.Success -> BreedsGridView(breedsStateFlow.breeds.toMutableStateList(), onClickPreviewPicture)
                    is BreedsUIState.Error -> LoadingView()
                }
            }
        }
    }
}

@Composable
internal fun FullscreenImageScreen(
    breed: Breed,
    back: () -> Unit
) {
    Box(Modifier.fillMaxSize().background(color = DogifyColors.fullScreenImageBackground)) {
        val scalableState = remember { ScalableState() }

        AsyncImage(
            url = breed.imageUrl,
            modifier =  Modifier.fillMaxSize().clipToBounds()
        )
        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .background(DogifyColors.filterButtonsBackground)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }

        TopLayout(
            alignLeftContent = {
                Box(modifier =
                    Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(DogifyColors.uiLightBlack)
                        .run {
                            clickable { back() }
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(IconCustomArrowBack, null, Modifier.size(34.dp), Color.White)
                }
            },
            alignRightContent = {},
        )
    }
}

@Composable
internal fun BreedsGridView(
    breeds: SnapshotStateList<Breed>,
    onClickPreviewPicture: (breed: Breed) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(top = 2.dp),
        columns = GridCells.Adaptive(minSize = 124.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        items(breeds) {
            CardView(it, onClickPreviewPicture)
        }
    }
}

@Composable
internal fun TopLayout(
    alignLeftContent: @Composable () -> Unit = {},
    alignRightContent: @Composable () -> Unit = {},
) {
    Box(
        Modifier
            .fillMaxWidth()
            .notchPadding()
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
internal fun CardView(
    breed: Breed,
    onClickPreviewPicture: (breed: Breed) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClickPreviewPicture(breed) },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                url = breed.imageUrl,
                modifier = Modifier.size(200.dp)
            )
            Row(Modifier.padding(vertical = 8.dp)) {
                Text(
                    text = breed.name,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
                Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
internal fun ProgressAnimation() {
    val dots = listOf(
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) }
    )

    dots.forEachIndexed { index, animatable ->
        LaunchedEffect(animatable) {
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1f, animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 2000
                        0.0f at 0 with LinearOutSlowInEasing // for 0-15 ms
                        1.0f at 200 with LinearOutSlowInEasing // for 15-75 ms
                        0.0f at 400 with LinearOutSlowInEasing // for 0-15 ms
                        0.0f at 2000
                    },
                    repeatMode = RepeatMode.Restart,
                )
            )
        }
    }

    val dys = dots.map { it.value }

    val travelDistance = with(LocalDensity.current) { 15.dp.toPx() }

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        dys.forEachIndexed { index, dy ->
            Box(
                Modifier
                    .size(25.dp)
                    .graphicsLayer {
                        translationY = -dy * travelDistance
                    },
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(color = Color.Gray, shape = CircleShape)
                )
            }

            if (index != dys.size - 1) {
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}

@Composable
internal fun LoadingView() {
    Scaffold {
        Column(
            Modifier
                .padding(4.dp)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProgressAnimation()
        }
    }
}