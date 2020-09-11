package hr.ferit.srdandragas.kunst.ui.home


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import hr.ferit.srdandragas.kunst.KunstApp

import hr.ferit.srdandragas.kunst.R
import hr.ferit.srdandragas.kunst.common.showFragment
import hr.ferit.srdandragas.kunst.db.FavouritesDatabase
import hr.ferit.srdandragas.kunst.model.Data
import hr.ferit.srdandragas.kunst.model.SearchArtistResponse
import hr.ferit.srdandragas.kunst.model.details.ArtDetails
import hr.ferit.srdandragas.kunst.networking.BackendFactory
import hr.ferit.srdandragas.kunst.repository.ArtDetailsRepository
import hr.ferit.srdandragas.kunst.ui.adapter.ArtistAdapter
import hr.ferit.srdandragas.kunst.ui.details.ArtistDetails
import kotlinx.android.synthetic.main.fragment_search_art.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchArt : Fragment() {

    private val adapter by lazy { ArtistAdapter(::onArtClicked) }
    val interactor = BackendFactory.getKunstInteractor()
    val repository = ArtDetailsRepository()
    private lateinit var name : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_art, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setOnClickListeners()
    }

    private fun setupUi() {


        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

    }

    private fun setOnClickListeners() {
        searchButton.setOnClickListener { getArtistResponse() }
    }

    private fun getArtistResponse() {
        name = searchEditText.text.toString()
        val map = mapOf<String, String>(Pair("q", name),  Pair("limit", "5"),Pair("has_image", "1"),Pair("indent", "3"))
        interactor.getArt(map, getArtistCallback())

    }

    private fun getArtistCallback(): Callback<SearchArtistResponse> = object : Callback<SearchArtistResponse> {
        override fun onFailure(call: Call<SearchArtistResponse>?, etTasksResponset: Throwable?) {

        }

        override fun onResponse(call: Call<SearchArtistResponse>?, response: Response<SearchArtistResponse>) {
            if (response.isSuccessful) {
                adapter.setData(response.body()!!.data)

            }

        }
    }

    private fun onArtClicked(art: Data){
            val selectedArt: ArtDetails = ArtDetails(art.title, art.images.web.url, art.wall_description, art.url)
            repository.onArtSelect(selectedArt)
            activity?.showFragment(R.id.frgamentContainer, ArtistDetails.newInstance())
    }

    companion object {
        fun newInstance(): Fragment {
            return SearchArt()
        }
    }
}
