package hr.ferit.srdandragas.kunst.ui.details


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.koushikdutta.ion.Ion
import hr.ferit.srdandragas.kunst.KunstApp


import hr.ferit.srdandragas.kunst.db.FavouritesDatabase
import hr.ferit.srdandragas.kunst.model.details.FavouritesDetails
import hr.ferit.srdandragas.kunst.repository.ArtDetailsRepository
import hr.ferit.srdandragas.kunst.ui.webView.ArtWebView
import hr.ferit.srdandragas.kunst.ui.webView.ArtWebView.Companion.WEB_URL
import kotlinx.android.synthetic.main.fragment_artist_details.*
import kotlinx.android.synthetic.main.item_artist.view.*
import com.google.firebase.firestore.FirebaseFirestore
import android.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import hr.ferit.srdandragas.kunst.model.details.ArtDetails
import java.util.*
import kotlin.collections.HashMap
class ArtistDetails : Fragment() {
    private val db = FavouritesDatabase.getInstance().favouritesDao()
    private val repository = ArtDetailsRepository()
    private var isFavourite = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(hr.ferit.srdandragas.kunst.R.layout.fragment_artist_details, container, false)
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

                favouriteIcon.setImageResource(hr.ferit.srdandragas.kunst.R.drawable.favourite_icon)
                isFavourite = true
            }
        }
    }

    private fun setOnClickListeners() {
        favouriteIcon.setOnClickListener { onFavouriteClicked() }
        tapForMore.setOnClickListener { onTapForMoreClicked() }
    }

    private fun onTapForMoreClicked(){
        val intent = Intent(KunstApp.ApplicationContext, ArtWebView::class.java)
        intent.putExtra(WEB_URL, repository.getSelectedArt().webUrl)
        startActivity(intent)
    }

    private fun onFavouriteClicked() {

        val selectedArt = repository.getSelectedArt()

        if(isFavourite == true)
        {
            db.removeFavourite(selectedArt.url)
            favouriteIcon.setImageResource(hr.ferit.srdandragas.kunst.R.drawable.favourite_empty_icon)
            return
        }
        val favourites: FavouritesDetails = FavouritesDetails(
            title = selectedArt.title,
            url = selectedArt.url,
            description = selectedArt.description,
            webUrl = selectedArt.webUrl,
            technique = selectedArt.technique
        )

        favouriteIcon.setImageResource(hr.ferit.srdandragas.kunst.R.drawable.favourite_icon)

        db.insert(favourites)
    }

    private fun setupUi() {
        val selectedArt = repository.getSelectedArt()
        artDetailsTitleText.text = selectedArt.title
        artistDetails.text = selectedArt.description
        Ion.with(artDetailsFeedImage)
            .placeholder(hr.ferit.srdandragas.kunst.R.drawable.ic_launcher_background)
            .load(selectedArt.url)
    }

    companion object {
        fun newInstance(): Fragment {
            return ArtistDetails()
        }
    }
}
