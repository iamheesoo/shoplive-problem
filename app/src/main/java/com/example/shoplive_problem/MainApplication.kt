package com.example.shoplive_problem

import android.app.Application
import com.example.shoplive_problem.data.di.dataModule
import com.example.shoplive_problem.domain.di.domainModule
import com.example.shoplive_problem.presentation.di.viewModule
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
        initLogger()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MainApplication)
            modules(viewModule + dataModule + domainModule)
        }
    }

    private fun initLogger() {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true)
            .methodCount(2)
            .methodOffset(0)
            .tag("SHOPLIVE_LOGGER")
            .build()

        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {})
    }
}