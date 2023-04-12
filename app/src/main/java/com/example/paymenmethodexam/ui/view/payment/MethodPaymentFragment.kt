package com.example.paymenmethodexam.ui.view.payment

import android.R
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.paymenmethodexam.data.network.Resource
import com.example.paymenmethodexam.databinding.FragmentMethodPaymentBinding
import com.example.paymenmethodexam.model.PaymentMethod
import com.example.paymenmethodexam.ui.model.SpinnerCustomAdapter
import com.example.paymenmethodexam.ui.viewmodel.AmountChargeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MethodPaymentFragment : Fragment() {

    private val viewModel by viewModels<AmountChargeViewModel>()
    private lateinit var mBinding: FragmentMethodPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMethodPaymentBinding.inflate(inflater, container, false)
        viewModel.getPaymentMethod()

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginResponse.observe(viewLifecycleOwner) {

            lifecycleScope.launch {
                when (it) {
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        var listPaymentMethod = mutableListOf<String>()
                        it.value.forEach {
                            listPaymentMethod.add(it.name)
                        }
                        fillSpinnerPaymentMethod(it.value)
                    }
                    is Resource.Failure -> {
                        hideLoading()
                        if (null != it.errorBody) {
                            println(it.errorBody)
                        } else {
                            println(it.toString())
                        }
                    }
                }
            }

        }
    }

    private fun showLoading() {
        mBinding.progressBar.visibility = View.VISIBLE
    }
    private fun hideLoading() {
        mBinding.progressBar.visibility = View.INVISIBLE
    }
    private fun fillSpinnerPaymentMethod(paymentMethodList: List<PaymentMethod>) {

        val adapter = SpinnerCustomAdapter(requireContext(), paymentMethodList)
        //val arrayAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, paymentMethodList)
        mBinding.dropdownPaymentMethod.adapter = adapter
        mBinding.dropdownPaymentMethod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(requireContext(), "selectes", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
}