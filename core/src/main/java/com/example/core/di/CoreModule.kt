package com.example.core.di


import androidx.room.Room
import com.example.core.BuildConfig
import com.example.core.data.local.LocalDataSource
import com.example.core.domain.repository.MoviesRepository
import com.example.core.data.local.database.MoviesDatabase
import com.example.core.data.remote.RemoteDataSource
import com.example.core.domain.repository.IMoviesRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        val hostname = "developer.themoviedb.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/OmnK2eeXWbxKHDQEkyF9yzoo+2dhL+W/4apAMUfWYSo=")
            .add(hostname, "sha256/OmnK2eeXWbxKHDQEkyF9yzoo+2dhL+W/4apAMUfWYSo=")
            .add(hostname, "sha256/OmnK2eeXWbxKHDQEkyF9yzoo+2dhL+W/4apAMUfWYSo=")
            .add(hostname, "sha256/OmnK2eeXWbxKHDQEkyF9yzoo+2dhL+W/4apAMUfWYSo=")
            .build()
        val token =
            BuildConfig.TOKEN
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .certificatePinner(certificatePinner)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val requestWithAuth = originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(requestWithAuth)
            }
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(com.example.core.data.remote.ApiService::class.java)
    }
}

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get()) }
    single<IMoviesRepository> { MoviesRepository(get(), get()) }
}

val databaseModule = module {
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("movies".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            get(),
            MoviesDatabase::class.java,
            "Movies1.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
    factory {
        get<MoviesDatabase>().moviesDao()
    }
}