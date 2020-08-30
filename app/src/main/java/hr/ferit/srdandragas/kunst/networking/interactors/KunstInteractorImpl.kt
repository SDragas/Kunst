package hr.ferit.srdandragas.kunst.networking.interactors

import hr.ferit.srdandragas.kunst.model.SearchArtistResponse
import hr.ferit.srdandragas.kunst.networking.KunstApiService
import retrofit2.Callback

class KunstInteractorImpl(private val KunstApiService: KunstApiService) : KunstInteractor {
    override fun getArt(artistName: Map<String, String>, getArtCallback: Callback<SearchArtistResponse>) {
        KunstApiService.getArt(artistName).enqueue (getArtCallback)
    }


}