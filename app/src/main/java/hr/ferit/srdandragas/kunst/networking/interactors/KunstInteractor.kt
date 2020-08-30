package hr.ferit.srdandragas.kunst.networking.interactors



import hr.ferit.srdandragas.kunst.model.SearchArtistResponse
import retrofit2.Callback

interface KunstInteractor {

    fun getArt( artistName: Map<String, String>, getArtCallback: Callback<SearchArtistResponse>)

}