@file:Suppress("KDocUnresolvedReference")

package com.example.screencontrol

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomNavigationDrawer(onCloseDrawer: () -> Unit) {
    ModalDrawerSheet(
        /**
         * [drawerContainerColor] can be used to change the drawer's background color
         */
//        drawerContainerColor = Color.LightGray,
        drawerShape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Drawer Title",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Divider(thickness = 1.dp)
        Text(modifier = Modifier
            .fillMaxWidth()
            .clickable { onCloseDrawer() }
            .padding(16.dp), text = "Close")
    }
}

@Preview(showSystemUi = true)
@Composable
fun CustomNavigationDrawerPreview() {
    CustomNavigationDrawer {}
}