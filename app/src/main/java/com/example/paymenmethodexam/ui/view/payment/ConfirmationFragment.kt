package com.example.paymenmethodexam.ui.view.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.paymenmethodexam.R
import com.example.paymenmethodexam.databinding.FragmentConfirmationBinding
import com.example.paymenmethodexam.model.ConfirmationPayment
import com.example.paymenmethodexam.utils.Constants.DATA_CHARGED
import com.google.gson.Gson

class ConfirmationFragment : Fragment() {
    private lateinit var _binding: FragmentConfirmationBinding
    private lateinit var confirmationPayment: ConfirmationPayment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmationBinding.inflate(inflater, container, false)
        arguments?.let {
            confirmationPayment = Gson().fromJson(it.getString(DATA_CHARGED), ConfirmationPayment::class.java)
        }
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(confirmationPayment) {
            _binding.tvAmount.text = amount.toString()
            _binding.tvPaymentMethod.text = paymentMethod
            _binding.tvNameBank.text = bank
            _binding.tvInstallments.text = installment
        }

        _binding.btnGoToHomeScreen.setOnClickListener {
            findNavController().navigate(R.id.action_confirmationFragment_to_amountChargeFragment, null)
        }
    }

}