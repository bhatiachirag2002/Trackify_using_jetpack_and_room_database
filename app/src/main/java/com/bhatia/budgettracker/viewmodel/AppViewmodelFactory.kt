package com.bhatia.budgettracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bhatia.budgettracker.repository.AppRepo


class AppViewModelFactory(
    private val appRepo: AppRepo
) : ViewModelProvider.Factory {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Check if the modelClass is assignable from AppViewModel
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            // Return an instance of AppViewModel with the required dependencies
            return AppViewModel(appRepo) as T
        }
        // Throw an exception if the ViewModel class is unknown
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}