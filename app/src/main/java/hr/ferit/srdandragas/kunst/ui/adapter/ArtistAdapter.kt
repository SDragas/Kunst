package hr.ferit.srdandragas.kunst.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.koushikdutta.ion.Ion
import hr.ferit.srdandragas.kunst.R
import hr.ferit.srdandragas.kunst.common.ArtClickListener
import hr.ferit.srdandragas.kunst.model.Data
import kotlinx.android.synthetic.main.item_artist.view.*

class ArtistAdapter(private val artClickListener: ArtClickListener): RecyclerView.Adapter<ArtistHolder>() {

    private val artist: MutableList<Data> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_artist, parent, false)
        return ArtistHolder(view)
    }

    override fun getItemCount(): Int = artist.size

    override fun onBindViewHolder(holder: ArtistHolder, position: Int) {
        holder.bind(artist[position], artClickListener )
    }

    fun setData(data: List<Data>) {
        this.artist.clear()
        this.artist.addAll(data)
        notifyDataSetChanged()
    }

    fun getData():List<Data> = artist
}

class ArtistHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(data: Data, artClickListener: ArtClickListener){
        itemView.setOnClickListener{ artClickListener(data) }
        itemView.artDetailsTitleText.text = data.title
        if(data.images.web.url != "null"){
            Ion.with(itemView.artDetailsFeedImage)
                .placeholder(R.drawable.ic_launcher_background)
                .load(data.images.web.url)
        }

    }
}