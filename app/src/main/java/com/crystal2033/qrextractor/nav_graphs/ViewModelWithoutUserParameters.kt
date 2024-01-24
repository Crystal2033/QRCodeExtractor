//package com.crystal2033.qrextractor.nav_graphs
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.ViewModel
//import androidx.navigation.NavBackStackEntry
//import androidx.navigation.NavController
//
//sealed class ViewModelWithoutUserParameters(){
//    companion object{
//        @Composable
//        inline fun <reified T : ViewModel> NavBackStackEntry.sharedHiltViewModel(navController: NavController): T {
//            val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
//            val parentEntry = remember(this) {
//                navController.getBackStackEntry(navGraphRoute)
//            }
//            return hiltViewModel(parentEntry)
//        }
//    }
//}
