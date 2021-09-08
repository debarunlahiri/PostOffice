package com.debarunlahiri.postoffice.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.debarunlahiri.postoffice.R
import com.debarunlahiri.postoffice.models.Pincode

class PincodeAdapter(var mContext: Context, var pincodeList: MutableList<Pincode>, var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<PincodeAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPincode: TextView = itemView.findViewById(R.id.tvPincode)
    }

    @JvmName("setPincodeList1")
    public fun setPincodeList(pincodeList: MutableList<Pincode>) {
        this.pincodeList = pincodeList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_pincode_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pincode = pincodeList[position]
        holder.tvPincode.text = pincode.Pincode
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(pincode)
        }

    }

    override fun getItemCount(): Int {
        return pincodeList.size
    }

    interface OnItemClickListener {
        public fun onItemClick(pincode: Pincode);
    }
}