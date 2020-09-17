package hr.ferit.srdandragas.kunst.ui.profile

object loggedUser {
    private var username = ""
     fun getUsername() = username
     fun setUsername(newUsername : String){
        username = newUsername
    }
}