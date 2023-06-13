package com.dogify.common

import com.dogify.common.domain.Breed

interface Page

expect class FullScreenPage(breed: Breed) : Page {
    val breed: Breed
}

expect class GalleryPage() : Page
