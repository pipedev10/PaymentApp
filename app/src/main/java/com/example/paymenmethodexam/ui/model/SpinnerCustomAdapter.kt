package com.example.paymenmethodexam.ui.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.paymenmethodexam.R
import com.example.paymenmethodexam.model.PaymentMethod

class SpinnerCustomAdapter (
    val context: Context,
    private val contentSpinner: List<PaymentMethod>): BaseAdapter() {

    override fun getCount(): Int = contentSpinner.count()

    override fun getItem(position: Int): Any {
        return contentSpinner[position]
    }

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_spinner_image, p2, false)
        val itemName = view.findViewById<TextView>(R.id.itemName)
        val imageName = view.findViewById<ImageView>(R.id.itemImage)

        itemName.text = contentSpinner[position].name
        Glide.with(context).load(contentSpinner[position].thumbnail).into(imageName)
        return view
    }

}