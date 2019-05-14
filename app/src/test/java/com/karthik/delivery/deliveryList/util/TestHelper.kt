package com.karthik.delivery.deliveryList.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer


/**
 *
 * Reference Lifecycle method code from internal project to mock observer get the lifecycle owner to work with
 *
 */

class TestLifecycleOwner : LifecycleOwner {
    private val registry: LifecycleRegistry = init()

    // Creates a LifecycleRegistry in RESUMED state.
    private fun init(): LifecycleRegistry {
        val registry = LifecycleRegistry(this)
        registry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        registry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        return registry
    }

    override fun getLifecycle(): Lifecycle {
        return registry
    }
}

fun <T> Observer<T>.testLifecycleOwner(): LifecycleOwner {
    return TestLifecycleOwner()
}