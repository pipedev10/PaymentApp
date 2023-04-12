package com.example.paymenmethodexam.ui.view.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.paymenmethodexam.data.network.Resource
import com.example.paymenmethodexam.databinding.FragmentMethodPaymentBinding
import com.example.paymenmethodexam.model.BankCard
import com.example.paymenmethodexam.model.Installments
import com.example.paymenmethodexam.model.PaymentMethod
import com.example.paymenmethodexam.ui.custom.SpinnerBankAdapter
import com.example.paymenmethodexam.ui.custom.SpinnerCustomAdapter
import com.example.paymenmethodexam.ui.custom.SpinnerCustomCreditAdapter
import com.example.paymenmethodexam.ui.viewmodel.AmountChargeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MethodPaymentFragment : Fragment() {

    private val viewModel by viewModels<AmountChargeViewModel>()
    private lateinit var mBinding: FragmentMethodPaymentBinding
    private val amount = 1000
    private lateinit var paymentMethodId: String
    private lateinit var idBank: String

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

        viewModel.paymentMethodResponse.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                when (it) {
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
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

        viewModel.bankCard.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                when (it) {
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        fillSpinnerBankCard(it.value)
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

        viewModel.installmentsResponse.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                when (it) {
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        println(it.value)
                        fillSpinnerInstallments(it.value)
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
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                paymentMethodId = paymentMethodList[position].id
                //Toast.makeText(requireContext(), paymentMethodList[position].id, Toast.LENGTH_SHORT).show()
                viewModel.getBankCard(paymentMethodId)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun fillSpinnerBankCard(bankCardList: List<BankCard>) {
        val adapter = SpinnerBankAdapter(requireContext(), bankCardList)
        mBinding.dropdownCardIssues.adapter = adapter
        mBinding.dropdownCardIssues.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                idBank = bankCardList[position].id
                //Toast.makeText(requireContext(), bankCardList[position].id, Toast.LENGTH_SHORT).show()
                viewModel.getInstallments(amount, paymentMethodId, idBank)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun fillSpinnerInstallments(installmentList: List<Installments>) {
        if (installmentList.isNotEmpty()) {
            val adapter = SpinnerCustomCreditAdapter(requireContext(), installmentList[0].payerCosts )
            mBinding.dropdownInstallments.adapter = adapter
            mBinding.dropdownInstallments.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                    //idBank = bankCardList[position].id
                    //Toast.makeText(requireContext(), bankCardList[position].id, Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        }else {
            Toast.makeText(requireContext(), "Lista Vacía", Toast.LENGTH_SHORT).show()
        }



    }
}