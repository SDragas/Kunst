package hr.ferit.srdandragas.kunst.networking

import hr.ferit.srdandragas.kunst.common.BASE_URL
import hr.ferit.srdandragas.kunst.networking.interactors.KunstInteractor
import hr.ferit.srdandragas.kunst.networking.interactors.KunstInteractorImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BackendFactory {

        private var retrofit: Retrofit? = null
        private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


        private val httpClient =
            OkHttpClient.Builder()
                .addInterceptor(interceptor)

                .build()

        private val client: Retrofit? = if (retrofit == null) createRetrofit() else retrofit

        private fun createRetrofit(): Retrofit? {
            retrofit = Retrofit.Builder()
                .client(httpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit
        }

        private fun getService(): KunstApiService = this.client!!.create(KunstApiService::class.java)

        fun getKunstInteractor(): KunstInteractor = KunstInteractorImpl(getService())
    }
