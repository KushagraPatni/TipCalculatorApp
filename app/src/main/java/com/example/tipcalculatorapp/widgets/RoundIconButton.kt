package com.example.tipcalculatorapp.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


val IconButtonSizeModifier=Modifier.size(40.dp)
@Composable
fun RoundedTconButton(
    modifier: Modifier=Modifier,
    imageVector: ImageVector,
    onClick: () ->Unit,
    tint: Color=Color.Black.copy(0.8f),
    backgroundColor: Color=MaterialTheme.colorScheme.background,//We are using onBackground so the app runs perfectly
                                                                   //even when the device is on dark mode
){Card(
    modifier = modifier
        .shadow(elevation = 0.dp)
        .padding(all = 4.dp)
        .clickable { onClick.invoke() }
        .then(IconButtonSizeModifier),
    shape = CircleShape,
    colors = CardDefaults.cardColors(containerColor = backgroundColor) // Updated for Material3
) {
    Icon(
        imageVector = imageVector,
        contentDescription = "Plus or Minus icon",
        tint = tint
    )
}
}