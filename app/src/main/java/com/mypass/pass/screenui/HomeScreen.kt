package com.mypass.pass.screenui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch


class NavigationViewModel: ViewModel() {
    var selectedItemIndex by mutableIntStateOf(0)
        private set
    fun setSelectedIndex(index: Int){
        selectedItemIndex = index
    }
}
data class NavigationItem
    (
    val title : String,
    val selectedIcon : ImageVector  ,
    val unSelectedIcon: ImageVector,
    val badgeCount: Int?=null
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: NavigationViewModel) {
    val navController= rememberNavController()
    val items = remember {
        listOf(
            NavigationItem(
                title = "Profile",
                selectedIcon = Icons.Filled.Person,
                unSelectedIcon = Icons.Outlined.Person,
            ),
            NavigationItem(
                title = "New Pass",
                selectedIcon = Icons.Filled.Edit,
                unSelectedIcon = Icons.Outlined.Edit, 
            ),
            NavigationItem(
                title = "Pass",
                selectedIcon = Icons.Filled.AccountBox,
                unSelectedIcon = Icons.Outlined.AccountBox,
            ),
        )
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet(modifier=Modifier
                    .width(230.dp)) {
                    Spacer(modifier = Modifier.height(16.dp))
                    items.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            label = { Text(text = item.title) },
                            selected = index == viewModel.selectedItemIndex,
                            onClick = {
                                viewModel.setSelectedIndex(index)
                                scope.launch {
                                    drawerState.close()
                                }
                                when(index){
                                    0-> navController.navigate("profile")
                                    1-> navController.navigate("new_pass")
                                    2-> navController.navigate("Pass")
                            }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == viewModel.selectedItemIndex) {
                                        item.selectedIcon
                                    } else item.unSelectedIcon,
                                    contentDescription = item.title
                                )
                            }
                        )
                    }
                }
            },
            drawerState = drawerState
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Menu") },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch { drawerState.open() } }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu"
                                )
                            }
                        }
                    )
                },

            ) {
                NavHost(navController = navController , startDestination = "profile") {
                    composable("profile") {
                        UserProfile()
                    }
                    composable("new_pass") {
                       ApplicationForm()

                    }
                    composable("Pass") {
                        PassData()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen(NavigationViewModel())
}