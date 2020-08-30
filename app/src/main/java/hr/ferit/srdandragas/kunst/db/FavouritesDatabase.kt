package hr.ferit.srdandragas.kunst.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hr.ferit.srdandragas.kunst.KunstApp
import hr.ferit.srdandragas.kunst.model.details.FavouritesDetails


@Database(version = 1, entities = arrayOf(FavouritesDetails::class), exportSchema = false)
abstract class FavouritesDatabase: RoomDatabase() {
    abstract fun favouritesDao(): FavouritesDao

    companion object {
        private const val NAME = "favourites_database"
        private var INSTANCE: FavouritesDatabase? = null

        fun getInstance(): FavouritesDatabase {
            if(INSTANCE == null) {

                INSTANCE = Room.databaseBuilder(
                    KunstApp.ApplicationContext,
                    FavouritesDatabase::class.java,
                    NAME)
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE as FavouritesDatabase
        }
    }

}