package com.example.eventfinder.data.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    private val _radius = MutableLiveData(50.0)
    val radius: LiveData<Double> get() = _radius

    private val _useDeviceLocation = MutableLiveData(true)
    val useDeviceLocation: LiveData<Boolean> get() = _useDeviceLocation

    fun setRadius(value: Double) {
        _radius.value = value
    }

    fun setUseDeviceLocation(value: Boolean) {
        _useDeviceLocation.value = value
    }
}