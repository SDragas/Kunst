package hr.ferit.srdandragas.kunst.model

data class Provenance (
    val description : String,
    val citations : List<Citations>,
    val footnotes : List<Footnotes>,
    val date : String
)