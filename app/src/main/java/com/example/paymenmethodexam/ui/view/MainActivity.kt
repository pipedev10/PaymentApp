package com.example.paymenmethodexam.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.paymenmethodexam.R
import com.example.paymenmethodexam.databinding.ActivityMainBinding
import com.example.paymenmethodexam.ui.view.payment.MethodPaymentFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }


}