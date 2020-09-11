package hr.ferit.srdandragas.kunst.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import hr.ferit.srdandragas.kunst.model.details.FavouritesDetails

@Dao
interface FavouritesDao {

    @Insert
    fun insert(favourite: FavouritesDetails)


    @Query("SELECT * FROM favourites")
    fun getAll(): List<FavouritesDetails>

    @Query("DELETE FROM favourites WHERE url = :url")
    fun removeFavourite(url: String)
}
