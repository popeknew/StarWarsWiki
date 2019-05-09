package com.example.nbainfoapp

import android.app.Application
import androidx.room.Room
import com.example.nbainfoapp.database.PeopleDatabase
import com.example.nbainfoapp.repository.PeopleDatabaseRepository
import com.example.nbainfoapp.repository.RepositoryRetrofit
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://swapi.co/api/"

class MyApplication: Application(), KodeinAware {

    override val kodein by Kodein.lazy {

        bind<OkHttpClient>() with singleton {
            val okHttpClient = OkHttpClient()
            okHttpClient
        }

        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                .client(OkHttpClient())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        }

        bind<RestApi>() with singleton {
            val retrofit: Retrofit = instance()
            retrofit.create(RestApi::class.java)
        }

        bind<RepositoryRetrofit>() with singleton {
            RepositoryRetrofit(instance())
        }

        bind<PeopleDatabase>() with singleton {
            Room.databaseBuilder(applicationContext, PeopleDatabase::class.java, "people.db")
                .fallbackToDestructiveMigration()
                .build()
        }

        bind<PeopleDatabaseRepository>() with singleton {
            PeopleDatabaseRepository(instance())
        }
    }
}