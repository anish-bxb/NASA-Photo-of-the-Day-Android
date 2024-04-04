import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unit5project.R

class FactAdapter(private val factList: List<List<String>>) : RecyclerView.Adapter<FactAdapter.FactViewHolder>() {

    // ViewHolder class to hold references to the views
    inner class FactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageURL: ImageView = itemView.findViewById(R.id.imageView)
        val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_fact, parent, false)
        return FactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        println("Bind Called")
        val currentItem = factList[position]
        Glide.with(holder.itemView.context)
            .load(currentItem[0]) // URL is at index 0
            .into(holder.imageURL)
        holder.textViewDate.text = currentItem[1]
        holder.textViewDescription.text = currentItem[2]
    }

    override fun getItemCount() = factList.size
}