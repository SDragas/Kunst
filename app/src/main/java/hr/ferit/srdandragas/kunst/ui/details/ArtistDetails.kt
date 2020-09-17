package hr.ferit.srdandragas.kunst.ui.details


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.koushikdutta.ion.Ion
import hr.ferit.srdandragas.kunst.KunstApp

import hr.ferit.srdandragas.kunst.R
import hr.ferit.srdandragas.kunst.db.FavouritesDatabase
import hr.ferit.srdandragas.kunst.model.details.FavouritesDetails
import hr.ferit.srdandragas.kunst.repository.ArtDetailsRepository
import hr.ferit.srdandragas.kunst.ui.profile.loggedUser
import hr.ferit.srdandragas.kunst.ui.webView.ArtWebView
import hr.ferit.srdandragas.kunst.ui.webView.ArtWebView.Companion.WEB_URL
import kotlinx.android.synthetic.main.fragment_artist_details.*
import kotlinx.android.synthetic.main.item_artist.view.*
import java.util.*

class ArtistDetails : Fragment() {
    private val db = FavouritesDatabase.getInstance().favouritesDao()
    private val repository = ArtDetailsRepository()
    private var isFavourite = false
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_artist_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
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
            database.child("favourites").child("user").child(selectedArt.title).removeValue()
            favouriteIcon.setImageResource(R.drawable.favourite_empty_icon)
            isFavourite = false
            return

        }
        val favourites: FavouritesDetails = FavouritesDetails(
            title = selectedArt.title,
            url = selectedArt.url,
            description = selectedArt.description,
            webUrl = selectedArt.webUrl,
            technique = selectedArt.technique,
            uid = getUser()
        )

        favouriteIcon.setImageResource(R.drawable.favourite_icon)
        isFavourite = true
        val favId = UUID.randomUUID().toString()
        //dohvatiti sve korisnike iz baze, searchat po mailu preko autorizacije, naci id usera
        //Log.d("+++",loggedUser.getUsername())
        //database.child("favourites").child(favourites.uid.toString()).setValue(favourites)
        database.child("favourites").child("user").child(favourites.title).setValue(favourites)
       // Log.d("fav", favourites.toString())
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

    private fun getUser() = auth.currentUser?.email.toString()



    companion object {
        fun newInstance(): Fragment {
            return ArtistDetails()
        }
    }
}