package hr.ferit.srdandragas.kunst.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import hr.ferit.srdandragas.kunst.R
import hr.ferit.srdandragas.kunst.common.showFragment
import hr.ferit.srdandragas.kunst.ui.favourites.Favourites
import hr.ferit.srdandragas.kunst.ui.profile.Profile
import kotlinx.android.synthetic.main.activity_home.*

class StartPage : AppCompatActivity() {

    private val onNavItemSelected  = BottomNavigationView.OnNavigationItemSelectedListener {
        when(it.itemId){
            R.id.home -> {
                showFragment(R.id.frgamentContainer, SearchArt.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.favourites -> {
                showFragment(R.id.frgamentContainer, Favourites.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.profile -> {
                showFragment(R.id.frgamentContainer, Profile.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            else -> return@OnNavigationItemSelectedListener false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupUi()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        bottomNavBar.setOnNavigationItemSelectedListener(onNavItemSelected)
    }

    private fun setupUi() {
        showFragment(R.id.frgamentContainer, SearchArt.newInstance())
    }
}
