package kg.bakai.cryptolist.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.bakai.cryptolist.R
import kg.bakai.cryptolist.domain.models.Crypto

class CryptoAdapter: RecyclerView.Adapter<CryptoAdapter.ViewHolder>() {
    private val list = mutableListOf<Crypto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.rv_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun submitItems(items: List<Crypto>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val image: ImageView = view.findViewById(R.id.iv_image)
        private val name: TextView = view.findViewById(R.id.tv_name)
        private val currency: TextView = view.findViewById(R.id.tv_currency)
        private val price: TextView = view.findViewById(R.id.tv_price)

        fun bind(item: Crypto) {
            Glide.with(image.context)
                .load(item.image)
                .into(image)

            name.text = item.name
            currency.text = item.symbol
            price.text = "$"+item.currentPrice.toString()
        }
    }
}