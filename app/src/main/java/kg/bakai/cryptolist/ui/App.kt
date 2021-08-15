package kg.bakai.cryptolist.ui

import android.app.Application
import kg.bakai.cryptolist.di.databaseModule
import kg.bakai.cryptolist.di.networkModule
import kg.bakai.cryptolist.di.repoModule
import kg.bakai.cryptolist.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(viewModelModule, repoModule, networkModule, databaseModule))
        }
    }
}