package hr.ferit.srdandragas.kunst.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import hr.ferit.srdandragas.kunst.KunstApp
import hr.ferit.srdandragas.kunst.R
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePassword : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        btn_confirm.setOnClickListener {
            onConfirmClicked()
        }
    }

    private fun onConfirmClicked() {
        if(new_password.text.toString().isNotEmpty() && confirm_password.text.toString().isNotEmpty() && current_password.text.toString().isNotEmpty())
        {
            if(new_password.text.toString() == confirm_password.text.toString())
            {
                val user = auth.currentUser
                if(user != null && user.email != null)
                {
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, current_password.text.toString())
                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                           if(it.isSuccessful)
                           {
                               Toast.makeText(baseContext, "Re-Authentication successful.",
                                   Toast.LENGTH_SHORT).show()
                               user!!.updatePassword(new_password.text.toString())
                                   .addOnCompleteListener { task ->
                                       if (task.isSuccessful) {
                                           Toast.makeText(baseContext, "Password changed successfully.",
                                               Toast.LENGTH_SHORT).show()
                                           startActivity(Intent(KunstApp.ApplicationContext, Profile::class.java))
                                           finish()
                                       }
                                   }
                           }
                            else
                           {
                               Toast.makeText(baseContext, "Re-Authentication failed.",
                                   Toast.LENGTH_SHORT).show()
                           }
                        }

                }
                else
                {
                    startActivity(Intent(KunstApp.ApplicationContext, ChangePassword::class.java))
                    finish()
                }
            }
            else
            {
                Toast.makeText(baseContext, "Passwords doesn't match.",
                    Toast.LENGTH_SHORT).show()
            }
        }
        else
        {
            Toast.makeText(baseContext, "Please fill all the fields.",
                Toast.LENGTH_SHORT).show()
        }
    }
}
