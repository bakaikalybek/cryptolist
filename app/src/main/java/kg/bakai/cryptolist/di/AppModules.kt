package kg.bakai.cryptolist.di

import android.app.Application
import androidx.room.Room
import kg.bakai.cryptolist.data.database.CryptoDao
import kg.bakai.cryptolist.data.database.CryptoDatabase
import kg.bakai.cryptolist.data.network.ApiService
import kg.bakai.cryptolist.data.repository.MainRepository
import kg.bakai.cryptolist.ui.viewmodels.MainViewModel
import kg.bakai.cryptolist.ui.viewmodels.SearchViewModel
import kg.bakai.cryptolist.utils.UrlProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}

val repoModule = module {
    single { MainRepository(get(), get(), get()) }
}

val networkModule = module {
    single {
        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        clientBuilder
            .addInterceptor(loggingInterceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)

        clientBuilder.build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(UrlProvider.baseUrl)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

val databaseModule = module {
    fun provideDatabase(application: Application): CryptoDatabase {
        return Room.databaseBuilder(application, CryptoDatabase::class.java, "crypto_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideCountriesDao(database: CryptoDatabase): CryptoDao {
        return database.dao
    }

    single { provideDatabase(get()) }
    single { provideCountriesDao(get()) }
}