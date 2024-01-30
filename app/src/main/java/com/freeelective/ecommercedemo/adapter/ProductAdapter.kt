package com.freeelective.ecommercedemo.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.freeelective.ecommercedemo.R
import com.freeelective.ecommercedemo.data.model.Product
import com.freeelective.ecommercedemo.ui.HomePageFragment

class ProductAdapter(
    private val context: Context,
    private val products: MutableList<Product>,
    private val onGridClick: HomePageFragment
) :

    BaseAdapter() {

    override fun getCount(): Int {
        return products.size
    }

    override fun getItem(p0: Int): Any {
        return products[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, currentView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder
        if (currentView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = currentView
            viewHolder = view.tag as ViewHolder
        }
        val products = getItem(position) as Product
        viewHolder.title.text = products.title
        viewHolder.price.text = "$${products.price}"
        viewHolder.description.text = products.description

        Glide.with(context).load(products.images.get(0)).into(viewHolder.image)

        view?.setOnClickListener {
            onGridClick.onItemClick(products.id, products.category)
        }
        return view
    }

    fun setData(newProducts: List<Product>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }

    private class ViewHolder(private val view: View) {
        val image = view.findViewById<ImageView>(R.id.image)
        val title = view.findViewById<TextView>(R.id.title)
        val price = view.findViewById<TextView>(R.id.price)
        val description = view.findViewById<TextView>(R.id.description)
    }
}