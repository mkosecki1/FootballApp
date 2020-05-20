package com.footballapp.ext

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController

fun runDestination(fragment: Fragment, destination: Int) {
    val navController = findNavController(fragment)
    navController.navigate(destination)
}