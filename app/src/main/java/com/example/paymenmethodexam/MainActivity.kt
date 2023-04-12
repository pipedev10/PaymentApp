package com.example.paymenmethodexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.paymenmethodexam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        /*mBinding.btnGoToCharge.setOnClickListener {
            val methodPaymentFragment = MethodPaymentFragment()
            val bundle = Bundle()
            bundle.putString("PRICE", mBinding.etPrice.text.toString())
            methodPaymentFragment.arguments = bundle
            supportFragmentManager.beginTransaction().add(R.id.frameLayout, methodPaymentFragment).commit()

         */
    }
}
