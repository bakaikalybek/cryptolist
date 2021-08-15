package kg.bakai.cryptolist.data.network

import kg.bakai.cryptolist.domain.models.Crypto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("coins/markets")
    fun fetchList(
        @Query("vs_currency") vs_currency: String,
        @Query("per_page") per_page: Int
    ): Call<List<Crypto>>
}