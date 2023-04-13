package com.example.paymenmethodexam.ui.view.payment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.paymenmethodexam.R
import com.example.paymenmethodexam.data.network.Resource
import com.example.paymenmethodexam.databinding.FragmentMethodPaymentBinding
import com.example.paymenmethodexam.model.BankCard
import com.example.paymenmethodexam.model.ConfirmationPayment
import com.example.paymenmethodexam.model.Installments
import com.example.paymenmethodexam.model.PaymentMethod
import com.example.paymenmethodexam.ui.custom.SpinnerBankAdapter
import com.example.paymenmethodexam.ui.custom.SpinnerCustomAdapter
import com.example.paymenmethodexam.ui.custom.SpinnerCustomCreditAdapter
import com.example.paymenmethodexam.ui.viewmodel.AmountChargeViewModel
import com.example.paymenmethodexam.utils.Constants.AMOUNT_TO_CHARGE
import com.example.paymenmethodexam.utils.Constants.DATA_CHARGED
import com.example.paymenmethodexam.utils.Constants.MESSAGE_ERROR_SERVICE
import com.example.paymenmethodexam.utils.Constants.TAG_ERROR
import com.example.paymenmethodexam.utils.loadUrl
import com.example.paymenmethodexam.utils.snackBar
import com.example.paymenmethodexam.utils.toUpperCase
import com.example.paymenmethodexam.utils.validateFields
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MethodPaymentFragment : Fragment() {

    private val viewModel by viewModels<AmountChargeViewModel>()
    private lateinit var _binding: FragmentMethodPaymentBinding
    private var amount = 0
    private lateinit var paymentMethodId: String
    private lateinit var idBank: String
    private lateinit var namePaymentMethod: String
    private lateinit var nameBank: String
    private lateinit var installment: String
    private var isValidForm = true


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

        setupTextField()
        setupObservers()
        setupButtons()
    }

    private fun setupButtons() {
        with(_binding){
            btnFinish.setOnClickListener {
                val bundle = Bundle()
                if (!validateFields(tilName)) return@setOnClickListener
                if (!isValidForm) requireView().snackBar(getString(R.string.error_form_incomplete))

                val confirmationData = ConfirmationPayment(amount, namePaymentMethod, nameBank, installment, tvNamePerson.text.toString())
                bundle.putString(DATA_CHARGED, Gson().toJson(confirmationData))
                findNavController().navigate(R.id.action_methodPaymentFragment_to_confirmationFragment, bundle)
            }
            btnBack.setOnClickListener {
                findNavController().navigate(R.id.action_methodPaymentFragment_to_amountChargeFragment, null)
            }
        }
    }

    private fun setupTextField() {
        with(_binding) {
            etName.toUpperCase(true)
            etName.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    tvNamePerson.text = value.toString().uppercase()
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
        }
    }

    private fun setupObservers() {
        with(viewModel) {
            paymentMethodResponse.observe(viewLifecycleOwner) {
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
                            isValidForm = false
                            it.errorBody?.let { responseError ->
                                Log.e(TAG_ERROR, responseError.toString())
                                requireView().snackBar(responseError.toString())
                            } ?: run {
                                requireView().snackBar(MESSAGE_ERROR_SERVICE)
                            }
                        }
                    }
                }
            }

            bankCard.observe(viewLifecycleOwner) {
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
                            isValidForm = false
                            it.errorBody?.let { responseError ->
                                Log.e(TAG_ERROR, responseError.toString())
                                requireView().snackBar(responseError.toString())
                            } ?: run {
                                requireView().snackBar(MESSAGE_ERROR_SERVICE)
                            }
                        }
                    }
                }
            }

            installmentsResponse.observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    when (it) {
                        is Resource.Loading -> {
                            showLoading()
                        }
                        is Resource.Success -> {
                            hideLoading()
                            fillSpinnerInstallments(it.value)
                        }
                        is Resource.Failure -> {
                            hideLoading()
                            isValidForm = false
                            it.errorBody?.let { responseError ->
                                Log.e(TAG_ERROR, responseError.toString())
                                requireView().snackBar(responseError.toString())
                            } ?: run {
                                requireView().snackBar(MESSAGE_ERROR_SERVICE)
                            }
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
                namePaymentMethod = paymentMethodList[position].name
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
                nameBank = bankCardList[position].name
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
                    installment = installmentList[0].payerCosts[position].recommendedMessage
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