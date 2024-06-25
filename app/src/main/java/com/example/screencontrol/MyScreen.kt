package com.example.screencontrol

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.SnackbarResult.Dismissed
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreen() {
    val context = LocalContext.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()
    var isShowingDialog by remember {
        mutableStateOf(false)
    }
    var isShowingBottomSheet by remember {
        mutableStateOf(false)
    }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        CustomNavigationDrawer {
            scope.launch {
                drawerState.close()
            }
        }
    }) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { MyTopBar() },
            bottomBar = {
                BottomBar {
                    scope.launch {
                        val snackbarResult = snackbarHostState.showSnackbar(
                            message = "Snackbar message",
                            actionLabel = "OK",
                            duration = SnackbarDuration.Short
                        )
                        when (snackbarResult) {
                            Dismissed -> Toast.makeText(
                                context,
                                "Snackbar dismissed",
                                Toast.LENGTH_SHORT
                            ).show()

                            ActionPerformed -> Toast.makeText(
                                context,
                                "Snackbar action clicked",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            },
            floatingActionButton = {
                MyFloatingActionButton {
                    isShowingDialog = true
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                    Snackbar(
                        snackbarData = snackbarData,
                        shape = RoundedCornerShape(50),
                        containerColor = Color.DarkGray,
                        contentColor = Color.White
                    )
                }
            }
        ) { scaffoldPadding ->
            Content(
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .fillMaxSize()
                    .padding(24.dp),
                onClickShowBottomSheet = {
                    isShowingBottomSheet = true
                },
                onClickShowNavigationDrawer = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            )
        }
    }

    if (isShowingDialog) {
        CustomDialog {
            isShowingDialog = false
        }
    }

    if (isShowingBottomSheet) {
        CustomBottomSheet(
            sheetState = bottomSheetState,
            onDismissRequest = { isShowingBottomSheet = false },
            onManualDismiss = {
                scope.launch {
                    bottomSheetState.hide()
                }.invokeOnCompletion {
                    if (!bottomSheetState.isVisible) {
                        isShowingBottomSheet = false
                    }
                }
            })
    }
}

@Composable
fun MyFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(
        shape = CircleShape,
        containerColor = Color.Black,
        contentColor = Color.White,
        onClick = onClick
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
    }
}

@Composable
fun MyTopBar() {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .shadow(6.dp)
            .background(Color.White)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Screen Control", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun BottomBar(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp)
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick
        ) {
            Text(text = "Continue", fontSize = 16.sp)
        }
    }
}

@Composable
fun Content(
    modifier: Modifier,
    onClickShowBottomSheet: () -> Unit,
    onClickShowNavigationDrawer: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(onClick = onClickShowBottomSheet) {
            Text(text = "Show bottom sheet")
        }
        Button(onClick = onClickShowNavigationDrawer) {
            Text(text = "Show navigation drawer")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MyScreenPreview() {
    MyScreen()
}