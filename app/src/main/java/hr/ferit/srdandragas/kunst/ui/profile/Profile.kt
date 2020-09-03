package hr.ferit.srdandragas.kunst.ui.profile


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import hr.ferit.srdandragas.kunst.KunstApp


import hr.ferit.srdandragas.kunst.R
import hr.ferit.srdandragas.kunst.ui.authentication.MainActivity
import hr.ferit.srdandragas.kunst.ui.home.StartPage
import kotlinx.android.synthetic.main.fragment_profile.*

class Profile : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setupUi()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        btn_sign_out.setOnClickListener{
            onSignOutClicked()
        }



        }

    private fun onSignOutClicked() {
        auth.signOut()
        startActivity(Intent(KunstApp.ApplicationContext, MainActivity::class.java))
        activity?.finish()
    }


    private fun setupUi() {
    }

    companion object {
        fun newInstance(): Fragment {
            return Profile()
        }
    }

}
