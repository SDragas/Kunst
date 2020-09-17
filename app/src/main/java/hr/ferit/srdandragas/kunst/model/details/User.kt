package hr.ferit.srdandragas.kunst.model.details

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User (
    var userId: Int,
    var email: String,
    var password: String
)