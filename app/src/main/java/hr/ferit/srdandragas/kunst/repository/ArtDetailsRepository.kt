package hr.ferit.srdandragas.kunst.repository

import hr.ferit.srdandragas.kunst.model.details.ArtDetails

class ArtDetailsRepository {
    companion object{
        private var selectedArt: ArtDetails = ArtDetails()
    }
    fun onArtSelect(art: ArtDetails){
        selectedArt = art
    }
    fun getSelectedArt(): ArtDetails = selectedArt
}
