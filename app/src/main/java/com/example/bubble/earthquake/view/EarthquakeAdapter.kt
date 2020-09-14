package com.example.bubble.earthquake.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bubble.earthquake.R
import com.example.bubble.earthquake.model.Earthquake
import kotlinx.android.synthetic.main.list_item_earthquake.view.*

/**
 * Customized Earthquake Adapter
 */
class EarthquakeAdapter(private val mContext: Context, earthquakes: MutableList<Earthquake>, listener: OnEarthquakeClickListener) :
    RecyclerView.Adapter<EarthquakeAdapter.DataViewHolder>() {
    private var dataList = earthquakes
    private var onEarthquakeClickListener: OnEarthquakeClickListener = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.list_item_earthquake, parent, false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val earthquake = dataList[position]
        // date and time
        holder.dateTimeTv.text = earthquake.datetime

        // location
        holder.locationTv.text = earthquake.lng.toString() + ", " + earthquake.lat.toString()

        // magnitude
        holder.magnitudeTv.text = earthquake.magnitude.toString()

        // depth
        holder.depthTv.text = earthquake.depth.toString()

        when {
            earthquake.magnitude >= 8.0 -> {
                holder.warningIv.visibility = View.VISIBLE
            } else -> {
                holder.warningIv.visibility = View.INVISIBLE
            }
        }
        holder.itemView.tag = position
    }

    fun updateDataSource(earthquakes: MutableList<Earthquake>) {
        dataList.clear()
        dataList.addAll(earthquakes)
        notifyDataSetChanged()
    }


    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val dateTimeTv: TextView = itemView.dtTv
        val locationTv: TextView = itemView.locTv
        val magnitudeTv: TextView = itemView.magTv
        val depthTv: TextView = itemView.depthTv
        val warningIv: ImageView = itemView.warningIv
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = v.tag as Int
            onEarthquakeClickListener.onClick(position, dataList[position])
        }
    }

    interface OnEarthquakeClickListener {
        fun onClick(position: Int, earthquake: Earthquake)
    }
}