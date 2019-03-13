package com.karthik.delivery.deliverylist.view

import android.databinding.DataBindingUtil
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.karthik.delivery.R
import com.karthik.delivery.base.util.OnItemClickListener
import com.karthik.delivery.base.util.loadImage
import com.karthik.delivery.databinding.ListItemDeliveryBinding
import com.karthik.delivery.deliverylist.data.Delivery


/**
 * created by Karthik A on 11/03/19
 */
class DeliveryListAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<DeliveryListAdapter.DeliveryListItemViewHolder>() {

    private var deliveryList: MutableList<Delivery> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): DeliveryListItemViewHolder {

        var inflater = LayoutInflater.from(parent.context)
        val binding: ListItemDeliveryBinding =
            DataBindingUtil.inflate(inflater, R.layout.list_item_delivery, parent, false)

        return DeliveryListItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return deliveryList.size
    }

    override fun onBindViewHolder(holder: DeliveryListItemViewHolder, pos: Int) {

        deliveryList[pos].let {
            holder.binding.delivery = it
            var recipientImageView: ImageView = holder.binding.root.findViewById(R.id.recipient_image)
            recipientImageView.loadImage(it.imageUrl)
        }

        holder.binding.root.findViewById<CardView>(R.id.delivery_card).setOnClickListener {
            listener.onItemClick(pos, deliveryList[pos])
        }
    }

    fun loadItems(items: List<Delivery>?) {
        items?.let {
            deliveryList.addAll(items)
            notifyItemRangeInserted(deliveryList.size, items.size)
        }
    }

    inner class DeliveryListItemViewHolder(var binding: ListItemDeliveryBinding) : RecyclerView.ViewHolder(binding.root)
}