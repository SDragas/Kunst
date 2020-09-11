package hr.ferit.srdandragas.kunst.ui.favourites


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import hr.ferit.srdandragas.kunst.R
import hr.ferit.srdandragas.kunst.common.showFragment
import hr.ferit.srdandragas.kunst.db.FavouritesDatabase
import hr.ferit.srdandragas.kunst.model.details.ArtDetails
import hr.ferit.srdandragas.kunst.model.details.FavouritesDetails
import hr.ferit.srdandragas.kunst.repository.ArtDetailsRepository
import hr.ferit.srdandragas.kunst.ui.adapter.ArtistAdapter
import hr.ferit.srdandragas.kunst.ui.adapter.FavouritesAdapter
import hr.ferit.srdandragas.kunst.ui.details.ArtistDetails
import kotlinx.android.synthetic.main.fragment_favourites.*

class Favourites : Fragment() {
    private val db = FavouritesDatabase.getInstance().favouritesDao()
    private val repository = ArtDetailsRepository()
    private val adapter by lazy { FavouritesAdapter(::onArtClicked) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setOnClickListeners()
    }

    private fun setupUi() {
        favouritesRecyclerView.layoutManager = LinearLayoutManager(context)
        favouritesRecyclerView.adapter = adapter
        adapter.setData(db.getAll())
    }

    private fun onArtClicked(art: FavouritesDetails){
        val selectedArt: ArtDetails = ArtDetails(art.title, art.url, art.description, art.url)
        repository.onArtSelect(selectedArt)
        activity?.showFragment(R.id.frgamentContainer, ArtistDetails.newInstance())
    }

    private fun setOnClickListeners() {
    }

    companion object {
        fun newInstance(): Fragment {
            return Favourites()
        }
    }
}
