package hr.ferit.srdandragas.kunst.ui.home


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import hr.ferit.srdandragas.kunst.KunstApp

import hr.ferit.srdandragas.kunst.R
import hr.ferit.srdandragas.kunst.common.showFragment
import hr.ferit.srdandragas.kunst.db.FavouritesDatabase
import hr.ferit.srdandragas.kunst.model.*
import hr.ferit.srdandragas.kunst.model.details.ArtDetails
import hr.ferit.srdandragas.kunst.model.details.CacheDetails
import hr.ferit.srdandragas.kunst.networking.BackendFactory
import hr.ferit.srdandragas.kunst.repository.ArtDetailsRepository
import hr.ferit.srdandragas.kunst.repository.PopularArtRepository
import hr.ferit.srdandragas.kunst.ui.adapter.ArtistAdapter
import hr.ferit.srdandragas.kunst.ui.details.ArtistDetails
import kotlinx.android.synthetic.main.fragment_search_art.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchArt : Fragment() {

    private val adapter by lazy { ArtistAdapter(::onArtClicked) }
    private val db = FavouritesDatabase.getInstance().favouritesDao()
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
        val popularRepository = PopularArtRepository().artist
        val cacheData = db.getAllCache()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        if(cacheData.isEmpty())
        adapter.setData(popularRepository)
        else
        {
            searchProgress.visibility = View.GONE
          popularRepository.clear()
            for(item in cacheData)
            {
                popularRepository.add(Data(title = item.title, url = item.webUrl, wall_description = item.description.orEmpty(),
                    images = Images(Web(item.url,"","","",""),Print("","","","",""), Full("","","","",""))
                    ))
            }
            adapter.setData(popularRepository)
        }
    }

    private fun setOnClickListeners() {
        searchButton.setOnClickListener { getArtistResponse() }
    }

    private fun getArtistResponse() {
        adapter.setData(listOf())
        searchProgress.visibility = View.VISIBLE
        name = searchEditText.text.toString()
        val map = mapOf<String, String>(Pair("q", name),  Pair("limit", "5"),Pair("has_image", "1"),Pair("indent", "3"))
        interactor.getArt(map, getArtistCallback())
        hideKeyboard()

    }

    private fun getArtistCallback(): Callback<SearchArtistResponse> = object : Callback<SearchArtistResponse> {
        override fun onFailure(call: Call<SearchArtistResponse>?, etTasksResponset: Throwable?) {

        }

        override fun onResponse(call: Call<SearchArtistResponse>?, response: Response<SearchArtistResponse>) {
            if (response.isSuccessful) {
                searchProgress.visibility = View.GONE
                db.removeCache()
                adapter.setData(response.body()!!.data)
                for(item in response.body()!!.data)
                {
                    db.insertCache(CacheDetails(title = item.title, url = item.images.web.url, description = item.wall_description, webUrl = item.url, technique = item.technique) )

                }
                if(adapter.getData().isEmpty())
                {
                    Toast.makeText(KunstApp.ApplicationContext, "No art found.",
                        Toast.LENGTH_SHORT).show()
                    adapter.setData(PopularArtRepository().artist)
                }

            }
        }
    }

    private fun onArtClicked(art: Data){
            val selectedArt: ArtDetails = ArtDetails(art.title, art.images.web.url, art.wall_description, art.url, art.technique)
            repository.onArtSelect(selectedArt)
            activity?.showFragment(R.id.frgamentContainer, ArtistDetails.newInstance())
    }

    private fun hideKeyboard()
    {
        val view = activity?.currentFocus
        if(view != null)
        {
            val hideMe = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideMe.hideSoftInputFromWindow(view.windowToken,0)
        }
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    companion object {
        fun newInstance(): Fragment {
            return SearchArt()
        }

    }
}
