package com.example.bubble.earthquake.network

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * @constructor {With Executor as input for time consuming operations}
 */
class AppExecutors internal constructor(diskIO: Executor, networkIO: Executor, mainThread: Executor){
    private val diskIOExecutor = diskIO
    private val networkIOExecutor = networkIO
    private val mainThreadExecutor = mainThread

    fun diskIO() : Executor {
        return diskIOExecutor
    }

    fun networkIO() : Executor {
        return networkIOExecutor
    }

    fun mainThread(): Executor {
        return mainThreadExecutor
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    companion object {
        private const val THREAD_COUNT = 3
        // Create a Singleton class
        val instance: AppExecutors by lazy(mode=LazyThreadSafetyMode.SYNCHRONIZED){
            AppExecutors(
                diskIO = Executors.newSingleThreadExecutor(),
                networkIO = Executors.newFixedThreadPool(THREAD_COUNT),
                mainThread = MainThreadExecutor()
            )
        }
    }
}