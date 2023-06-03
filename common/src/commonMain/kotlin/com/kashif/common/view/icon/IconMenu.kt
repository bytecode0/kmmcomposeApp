package com.kashif.common.view.icon

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath

val IconMenu = materialIcon(name = "Filled.Menu") {
    materialPath {
        moveTo(3.0f, 18.0f)
        horizontalLineToRelative(18.0f)
        verticalLineToRelative(-2.0f)
        lineTo(3.0f, 16.0f)
        verticalLineToRelative(2.0f)
        close()
        moveTo(3.0f, 13.0f)
        horizontalLineToRelative(18.0f)
        verticalLineToRelative(-2.0f)
        lineTo(3.0f, 11.0f)
        verticalLineToRelative(2.0f)
        close()
        moveTo(3.0f, 6.0f)
        verticalLineToRelative(2.0f)
        horizontalLineToRelative(18.0f)
        lineTo(21.0f, 6.0f)
        lineTo(3.0f, 6.0f)
        close()
    }
}
