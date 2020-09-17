package hr.ferit.srdandragas.kunst.db

object idHandler {
    lateinit var favID: String

    private fun getFavId() = favID

    private fun setFavId(newId: String) {
        favID = newId

    }
}



