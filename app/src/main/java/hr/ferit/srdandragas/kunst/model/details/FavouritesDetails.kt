package hr.ferit.srdandragas.kunst.model.details

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class FavouritesDetails (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "webUrl") val webUrl: String,
    @ColumnInfo(name = "technique") val technique: String
)
