package com.example.paymenmethodexam.ui.custom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.paymenmethodexam.R
import com.example.paymenmethodexam.model.BankCard
import com.example.paymenmethodexam.model.Installments
import com.example.paymenmethodexam.model.PayerCosts
import com.example.paymenmethodexam.model.PaymentMethod
import com.example.paymenmethodexam.utils.load
import com.example.paymenmethodexam.utils.loadGif

class SpinnerCustomAdapter (
    private val context: Context,
    private val contentSpinner: List<PaymentMethod>): BaseAdapter() {

    override fun getCount(): Int = contentSpinner.count()

    override fun getItem(position: Int): Any = contentSpinner[position]

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

class SpinnerBankAdapter (
    private val context: Context,
    private val contentSpinner: List<BankCard>): BaseAdapter() {

    override fun getCount(): Int = contentSpinner.count()

    override fun getItem(position: Int): Any = contentSpinner[position]

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_spinner_image, p2, false)
        val itemName = view.findViewById<TextView>(R.id.itemName)
        val imageName = view.findViewById<ImageView>(R.id.itemImage)

        itemName.text = contentSpinner[position].name
        if (contentSpinner[position].thumbnail.contains(".gif")) {
            imageName.loadGif(contentSpinner[position].thumbnail)
        } else {
            imageName.load(contentSpinner[position].thumbnail)
        }
        return view
    }
}

class SpinnerCustomCreditAdapter (
    private val context: Context,
    private val contentSpinner: List<PayerCosts>): BaseAdapter() {

    override fun getCount(): Int = contentSpinner.count()

    override fun getItem(position: Int): Any = contentSpinner[position]

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_spinner_image, p2, false)
        val itemName = view.findViewById<TextView>(R.id.itemName)
        val imageName = view.findViewById<ImageView>(R.id.itemImage)

        itemName.text = contentSpinner[position].recommendedMessage
        /*if (contentSpinner[position].thumbnail.contains(".gif")) {
            imageName.loadGif(contentSpinner[position].thumbnail)
        } else {
            imageName.load(contentSpinner[position].thumbnail)
        }*/
        return view
    }
}