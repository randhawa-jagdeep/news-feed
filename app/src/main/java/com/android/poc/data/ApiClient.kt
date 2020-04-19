package  com.android.poc.data

import com.android.poc.model.Feeds
import com.android.poc.model.FeedsRepository
import com.android.poc.utils.Helper
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

object ApiClient {
    private var servicesApiInterface:ServicesApiInterface?=null

    fun build():ServicesApiInterface?{
        val gson = GsonBuilder()
            .setLenient()
            .create()
        var builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(Helper.URLS.BASE.url)
            .addConverterFactory(GsonConverterFactory.create(gson))
        var httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor())

        var retrofit: Retrofit = builder.client(httpClient.build()).build()
        servicesApiInterface = retrofit.create(
            ServicesApiInterface::class.java)

        return servicesApiInterface as ServicesApiInterface
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level= HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    interface ServicesApiInterface {
         @GET()
         fun getFeeds(@Url path:String): Call<Feeds>

    }}