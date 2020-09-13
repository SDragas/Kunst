package hr.ferit.srdandragas.kunst.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import hr.ferit.srdandragas.kunst.model.details.CacheDetails
import hr.ferit.srdandragas.kunst.model.details.FavouritesDetails

@Dao
interface FavouritesDao {

    @Insert
    fun insert(favourite: FavouritesDetails)


    @Query("SELECT * FROM favourites")
    fun getAll(): List<FavouritesDetails>

    @Query("DELETE FROM favourites WHERE url = :url")
    fun removeFavourite(url: String)

    @Insert
    fun insertCache(cache: CacheDetails)

    @Query("SELECT * FROM cache")
    fun getAllCache(): List<CacheDetails>

    @Query("DELETE FROM cache")
    fun removeCache()
}
