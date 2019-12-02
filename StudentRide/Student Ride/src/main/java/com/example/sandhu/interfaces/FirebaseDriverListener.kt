package com.example.sandhu.interfaces

import com.example.sandhu.model.Driver


interface FirebaseDriverListener {

    fun onDriverAdded(driver: Driver)

    fun onDriverRemoved(driver: Driver)

    fun onDriverUpdated(driver: Driver)
}