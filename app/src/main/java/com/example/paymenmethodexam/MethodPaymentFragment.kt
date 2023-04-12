package com.example.paymenmethodexam

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.paymenmethodexam.databinding.FragmentMethodPaymentBinding


class MethodPaymentFragment : Fragment() {

    private lateinit var mBinding: FragmentMethodPaymentBinding
    private lateinit var value: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMethodPaymentBinding.inflate(inflater, container, false)
        value = arguments?.getString("PRICE").toString()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}