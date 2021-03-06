package com.footballapp

import com.footballapp.net.ConnectionManager
import com.footballapp.net.RestApi
import com.footballapp.repository.Repository
import com.footballapp.ui.login.LoginViewModel
import com.footballapp.ui.register.RegisterViewModel
import com.footballapp.ui.scorers.ScorersViewModel
import com.footballapp.ui.standings.StandingsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val HEADER_NAME = "X-Auth-Token"
const val HEADER_VALUE = "97a5f465440a4bd4bbe735a791adbc78"

val viewModelModule = module {
    viewModel { LoginViewModel(repository = get()) }
    viewModel { RegisterViewModel(repository = get()) }
    viewModel { ScorersViewModel(repository = get()) }
    viewModel { StandingsViewModel(repository = get()) }
}

val repositoryModule = module {
    single {
        Repository(get(), get())
    }
}

val firebaseAuthModule = module {
    single {
        FirebaseAuth.getInstance()
    }
}

val connectionModule = module {
    single {
        ConnectionManager()
    }
}

val apiModule = module {
    fun provideRestApi(okHttpClient: OkHttpClient, gson: Gson): RestApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
            .create(RestApi::class.java)
    }

    single { provideRestApi(get(), get()) }
}

val networkModule = module {
    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideOkHttp(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .followRedirects(true)
            .followSslRedirects(true)
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader(HEADER_NAME, HEADER_VALUE)
                    .build()
                chain.proceed(newRequest)
            }
        return builder.build()
    }

    single { provideGson() }
    single { provideOkHttp() }
}