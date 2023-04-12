package com.example.paymenmethodexam.ui.view.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.paymenmethodexam.data.network.Resource
import com.example.paymenmethodexam.databinding.FragmentMethodPaymentBinding
import com.example.paymenmethodexam.ui.viewmodel.AmountChargeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MethodPaymentFragment : Fragment() {

    private val viewModel by viewModels<AmountChargeViewModel>()
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
        viewModel.getPaymentMethod()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //mBinding.tvPrice.text = value

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer { it ->

            lifecycleScope.launch {
                it.getContentIfNotHandled()?.let {
                    when(it) {
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            println(it.value)
                        }
                        is Resource.Failure -> {
                            //hideLoading()
                            if (null != it.errorBody) {
                                println(it.errorBody)
                            }else {
                                println("Error")
                            }
                            //handleApiError(it) { showError () /* goToScreenIncorrectData()*/ }
                        }
                    }
                }


            }

        })
    }
}