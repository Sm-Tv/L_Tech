package sm_tv.com.l_tech.adapters

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import sm_tv.com.l_tech.R
import sm_tv.com.l_tech.fragments.models.Item
import sm_tv.com.l_tech.util.Constants.BASE_URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ContentAdapter : RecyclerView.Adapter<ContentAdapter.MyViewHolder>() {

    private var paramSort = false

    private var sortedList = SortedList(Item::class.java, object : SortedList.Callback<Item>() {

        override fun onChanged(position: Int, count: Int) {
            notifyItemRangeChanged(position, count)
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(item1: Item, item2: Item): Boolean {
            return false
        }

        override fun onInserted(position: Int, count: Int) {
            notifyItemRangeInserted(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            notifyItemRangeRemoved(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            notifyItemMoved(fromPosition, toPosition)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun compare(o1: Item, o2: Item): Int {
            val date1 = convertStringInToData(o1.date)
            val date2 = convertStringInToData(o2.date)
            return if (paramSort && date2 > date1) {
                -1
            } else {
                1
            }
        }
    })


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var oneItem: Item
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private val tvData: TextView = itemView.findViewById(R.id.tvData)
        private val imAvatar: ImageView = itemView.findViewById(R.id.imAvatar)
        private val oneItemConstraint: ConstraintLayout = itemView.findViewById(R.id.oneItemConstraint)

        fun bind(item: Item) {
            oneItem = item
            tvTitle.text = oneItem.title
            tvDescription.text = oneItem.text
            tvData.text = oneItem.date
            val uri = Uri.parse(BASE_URL + oneItem.image)
            Picasso.get()
                .load(uri)
                .resize(200, 200)
                .error(R.drawable.ic_baseline_refresh_24)
                .into(imAvatar, object : Callback {
                    override fun onSuccess() {
                        println("__________________ONSUCCESS")
                    }

                    override fun onError(e: Exception?) {
                        println("____________________$e")
                    }
                })
            oneItemConstraint.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("title", oneItem.title)
                bundle.putString("description", oneItem.text)
                bundle.putString("imageUri", oneItem.image)
                itemView.findNavController().navigate(R.id.action_contentFragment_to_descriptionFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return ContentAdapter.MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_one, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        sortedList.let { holder.bind(it.get(position)) }
    }

    override fun getItemCount(): Int {
        return sortedList.size()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertStringInToData(str: String): LocalDateTime {
        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")
        return LocalDateTime.parse(str, format)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSortItems(item: List<Item>, sort: Boolean) {
        paramSort = sort
        sortedList.replaceAll(item)
    }
}