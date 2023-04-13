package com.example.paymenmethodexam.ui.view.payment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
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
import com.example.paymenmethodexam.utils.Constants.AMOUNT_TO_CHARGE
import com.example.paymenmethodexam.utils.loadUrl
import com.example.paymenmethodexam.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MethodPaymentFragment : Fragment() {

    private val viewModel by viewModels<AmountChargeViewModel>()
    private lateinit var _binding: FragmentMethodPaymentBinding
    private var amount = 0
    private lateinit var paymentMethodId: String
    private lateinit var idBank: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMethodPaymentBinding.inflate(inflater, container, false)
        viewModel.getPaymentMethod()
        arguments?.let {
            amount = it.getInt(AMOUNT_TO_CHARGE)
        }
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.etName.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
                _binding.tvNamePerson.text = value.toString().uppercase()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        /*_binding.etDateExpire.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {

                value?.let {
                    if (it.length in 1..4) {
                        _binding.tvFirstNumbers.text = value.substring(0, value.length)
                    } else if (it.length in 5..8) {
                        _binding.tvSecondNumbers.text = value.substring(4, value.length)
                    } else if (it.length in 9..12) {
                        _binding.tvThirdNumbers.text = value.substring(8, value.length)
                    } else if (it.length in 13..16) {
                        _binding.tvFourthNumbers.text = value.substring(12, value.length)
                    }
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })*/
        _binding.etDateExpire.setOnFocusChangeListener { _, hasFocus ->
            val numberCard = _binding.etDateExpire.text.toString()
            if (!hasFocus) {
                with(numberCard) {
                    val num = this.length / 16
                    println(num)
                    if (this.length in 1..4) {
                        _binding.tvFirstNumbers.text = this.substring(0, this.length)
                    } else if (this.length in 5..8) {
                        _binding.tvSecondNumbers.text = this.substring(4, this.length)
                    } else if (this.length in 9..12) {
                        _binding.tvThirdNumbers.text = this.substring(8, this.length)
                    } else if (this.length in 13..16) {
                        _binding.tvFourthNumbers.text = this.substring(12, this.length)
                    }
                }

            }
        }
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
                            requireView().snackBar(it.errorBody.toString())
                        } else {
                            println(it.toString())
                        }
                    }
                }
            }
        }
    }

    private fun showLoading() {
        _binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideLoading() {
        _binding.progressBar.visibility = View.INVISIBLE
    }
    private fun fillSpinnerPaymentMethod(paymentMethodList: List<PaymentMethod>) {
        val adapter = SpinnerCustomAdapter(requireContext(), paymentMethodList)
        _binding.dropdownPaymentMethod.adapter = adapter
        _binding.dropdownPaymentMethod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                paymentMethodId = paymentMethodList[position].id
                loadImagePayment(paymentMethodList[position].thumbnail)
                viewModel.getBankCard(paymentMethodId)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun fillSpinnerBankCard(bankCardList: List<BankCard>) {
        val adapter = SpinnerBankAdapter(requireContext(), bankCardList)
        _binding.dropdownCardIssues.adapter = adapter
        _binding.dropdownCardIssues.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                idBank = bankCardList[position].id
                viewModel.getInstallments(amount, paymentMethodId, idBank)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun fillSpinnerInstallments(installmentList: List<Installments>) {
        if (installmentList.isNotEmpty()) {
            val adapter = SpinnerCustomCreditAdapter(requireContext(), installmentList[0].payerCosts )
            _binding.dropdownInstallments.adapter = adapter
            _binding.dropdownInstallments.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                    _binding.btnFinish.isEnabled = true
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}

            }
        }else {
            requireView().snackBar("No se logró obtener cuotas")
        }
    }

    private fun loadImagePayment(url: String) {
        _binding.imgType.loadUrl(url)
    }
}