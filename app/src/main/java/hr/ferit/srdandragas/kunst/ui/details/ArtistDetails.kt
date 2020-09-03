package hr.ferit.srdandragas.kunst.ui.details


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.koushikdutta.ion.Ion

import hr.ferit.srdandragas.kunst.R
import hr.ferit.srdandragas.kunst.db.FavouritesDatabase
import hr.ferit.srdandragas.kunst.model.details.FavouritesDetails
import hr.ferit.srdandragas.kunst.repository.ArtDetailsRepository
import kotlinx.android.synthetic.main.fragment_artist_details.*
import kotlinx.android.synthetic.main.item_artist.view.*

class ArtistDetails : Fragment() {
    private val db = FavouritesDatabase.getInstance().favouritesDao()
    private val repository = ArtDetailsRepository()
    private var isFavourite = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_artist_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkFavourite()
        setupUi()
        setOnClickListeners()
    }

    private fun checkFavourite() {
        val savedArt = db.getAll()
        val selectedArt = repository.getSelectedArt()
        for(item in savedArt)
        {
            if(item.url == selectedArt.url)
            {

                favouriteIcon.setImageResource(R.drawable.favourite_icon)
                isFavourite = true
            }
        }
    }

    private fun setOnClickListeners() {
        favouriteIcon.setOnClickListener { onFavouriteClicked() }
    }

    private fun onFavouriteClicked() {

        val selectedArt = repository.getSelectedArt()

        if(isFavourite == true)
        {
            db.removeFavourite(selectedArt.url)
            favouriteIcon.setImageResource(R.drawable.favourite_empty_icon)
            return
        }
        val favourites: FavouritesDetails = FavouritesDetails(
            title = selectedArt.title,
            url = selectedArt.url,
            description = selectedArt.description
        )

        favouriteIcon.setImageResource(R.drawable.favourite_icon)
        db.insert(favourites)
    }

    private fun setupUi() {
        val selectedArt = repository.getSelectedArt()
        artDetailsTitleText.text = selectedArt.title
        artistDetails.text = selectedArt.description
        Ion.with(artDetailsFeedImage)
            .placeholder(R.drawable.ic_launcher_background)
            .load(selectedArt.url)
    }

    companion object {
        fun newInstance(): Fragment {
            return ArtistDetails()
        }
    }
}
