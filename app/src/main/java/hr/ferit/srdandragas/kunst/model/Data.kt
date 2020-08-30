package hr.ferit.srdandragas.kunst.model

data class Data(
    val id : Int,
    val accession_number : String,
    val share_license_status : String,
    val tombstone : String,
    val current_location : String,
    val title : String,
    val title_in_original_language : String,
    val series : String,
    val series_in_original_language : String,
    val creation_date : String,
    val creation_date_earliest : Int,
    val creation_date_latest: Int,
    val creators : List<Creators>,
    val culture : List<String>,
    val technique : String,
    val support_materials : List<SupportMaterials>,
    val department : String,
    val collection : String,
    val type : String,
    val measurements : String,
    val dimensions : Dimensions,
    val state_of_the_work : String,
    val edition_of_the_work : String,
    val creditline : String,
    val copyright : String,
    val inscriptions : List<Inscriptions>,
    val exhibitions : Exhibitions,
    val provenance : List<Provenance>,
    val find_spot : String,
    val related_works : List<RelatedWorks>,
    val fun_fact : String,
    val digital_description : String,
    val wall_description : String,
    val citations : List<Citations>,
    val catalogue_raisonne : String,
    val url : String,
    val images : Images,
    val updated_at : String
)