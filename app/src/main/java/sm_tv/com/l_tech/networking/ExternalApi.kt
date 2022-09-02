package sm_tv.com.l_tech.networking

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import sm_tv.com.l_tech.fragments.models.Item
import sm_tv.com.l_tech.networking.models.PhoneMask
import sm_tv.com.l_tech.networking.models.ServerResponseAfterAuth

interface ExternalApi {
    @GET("./api/v1/phone_masks")
    suspend fun getMaskFromServer(): Response<PhoneMask>


    @POST("./api/v1/auth")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    suspend fun chekAuth(
        @Query("phone") phone: String,
        @Query("password") password: String
    ): Response<ServerResponseAfterAuth>

    @GET("./api/v1/posts")
    suspend fun getListItem(): Response<List<Item>>
}
