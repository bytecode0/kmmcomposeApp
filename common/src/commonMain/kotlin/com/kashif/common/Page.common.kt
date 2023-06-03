package com.kashif.common

import com.kashif.common.domain.Breed

interface Page

expect class FullScreenPage(breed: Breed) : Page {
    val breed: Breed
}

expect class GalleryPage() : Page
