package hr.ferit.srdandragas.kunst.model

data class Exhibitions(
    val current : List<Current>,
    val legacy : List<String>
)