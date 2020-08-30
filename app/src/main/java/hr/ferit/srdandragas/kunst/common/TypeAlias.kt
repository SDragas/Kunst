package hr.ferit.srdandragas.kunst.common

import hr.ferit.srdandragas.kunst.model.Data
import hr.ferit.srdandragas.kunst.model.details.FavouritesDetails

typealias ArtClickListener = (art: Data) -> Unit

typealias FavouritesClickListener = (art: FavouritesDetails) -> Unit