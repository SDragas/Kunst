package hr.ferit.srdandragas.kunst.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.koushikdutta.ion.Ion
import hr.ferit.srdandragas.kunst.R
import hr.ferit.srdandragas.kunst.common.FavouritesClickListener
import hr.ferit.srdandragas.kunst.model.Data
import hr.ferit.srdandragas.kunst.model.details.FavouritesDetails
import kotlinx.android.synthetic.main.item_artist.view.*

class FavouritesAdapter(private val artClickListener: FavouritesClickListener): RecyclerView.Adapter<DataHolder>()
{
    private val data: MutableList<FavouritesDetails> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_artist, parent, false)
        return DataHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.bind(data[position], artClickListener)
    }

    fun setData(data: List<FavouritesDetails>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
}
class DataHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(data: FavouritesDetails, artClickListener: FavouritesClickListener){
        itemView.setOnClickListener{ artClickListener(data) }
        itemView.artDetailsTitleText.text = data.title
        Ion.with(itemView.artDetailsFeedImage)
            .placeholder(R.drawable.ic_launcher_background)
            .load(data.url)
    }
}