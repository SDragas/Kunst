package hr.ferit.srdandragas.kunst.networking

import hr.ferit.srdandragas.kunst.model.SearchArtistResponse
import retrofit2.Call
import retrofit2.http.*

interface KunstApiService {

    @GET("?")
    fun getArt(@QueryMap queryMap: Map<String, String>): Call<SearchArtistResponse>
}
