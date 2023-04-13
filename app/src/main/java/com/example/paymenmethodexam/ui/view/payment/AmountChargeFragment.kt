package com.example.paymenmethodexam.ui.view.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.paymenmethodexam.databinding.FragmentAmountChargeBinding
import androidx.navigation.fragment.findNavController
import com.example.paymenmethodexam.R
import com.example.paymenmethodexam.utils.Constants.AMOUNT_TO_CHARGE
import com.example.paymenmethodexam.utils.validateFields

class AmountChargeFragment : Fragment() {

    private lateinit var _binding: FragmentAmountChargeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAmountChargeBinding.inflate(inflater, container,false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTextField()
        setupButtons()
    }

    private fun setupTextField() {
        with(_binding) {
            etPrice.addTextChangedListener { validateFields(tilPrice) }
        }
    }

    private fun setupButtons() {
        with(_binding) {
            btnGoToCharge.setOnClickListener { if (validateFields(tilPrice)) goToPaymentMethod() }
        }
    }

    private fun goToPaymentMethod(){
        val bundle = Bundle()
        bundle.putInt(AMOUNT_TO_CHARGE, _binding.etPrice.text.toString().toInt())
        findNavController().navigate(R.id.action_amountChargeFragment_to_methodPaymentFragment, bundle)
    }
}