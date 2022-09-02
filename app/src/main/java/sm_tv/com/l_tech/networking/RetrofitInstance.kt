package sm_tv.com.l_tech.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sm_tv.com.l_tech.util.Constants.BASE_URL

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: ExternalApi by lazy {
        retrofit.create(ExternalApi::class.java)
    }
}
