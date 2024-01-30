package com.freeelective.ecommercedemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.freeelective.ecommercedemo.R
import com.freeelective.ecommercedemo.ui.ProductsDetailsFragment

class ImagePageAdapter(private val context: ProductsDetailsFragment, private val imageUrls: List<String>) : PagerAdapter() {
    override fun getCount(): Int {
        return imageUrls.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context.requireContext())
        val view = inflater.inflate(R.layout.item_images, container, false)

        val imageView: ImageView = view.findViewById(R.id.imageView)
        Glide.with(context)
            .load(imageUrls[position])
            .into(imageView)

        container.addView(view)

        return view
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}