package com.example.paymenmethodexam.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paymenmethodexam.data.network.Resource
import com.example.paymenmethodexam.model.BankCard
import com.example.paymenmethodexam.model.Installments
import com.example.paymenmethodexam.model.PaymentMethod
import com.example.paymenmethodexam.repository.AmountChargeImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AmountChargeViewModel @Inject constructor(
    private val amountChargeImp: AmountChargeImp
): ViewModel() {

    private val _paymentMethodResponse: MutableLiveData<Resource<List<PaymentMethod>>> = MutableLiveData()
    val paymentMethodResponse: LiveData<Resource<List<PaymentMethod>>>
        get() = _paymentMethodResponse

    private val _bankCardResponse: MutableLiveData<Resource<List<BankCard>>> = MutableLiveData()
    val bankCard: LiveData<Resource<List<BankCard>>>
        get() = _bankCardResponse

    private val _installmentsResponse: MutableLiveData<Resource<List<Installments>>> = MutableLiveData()
    val installmentsResponse: LiveData<Resource<List<Installments>>>
        get() = _installmentsResponse

    fun getPaymentMethod() = viewModelScope.launch {
        _paymentMethodResponse.value = Resource.Loading
        _paymentMethodResponse.value = amountChargeImp.getPaymentMethod()
    }

    fun getBankCard(paymentMethodId: String) = viewModelScope.launch {
        _bankCardResponse.value = Resource.Loading
        _bankCardResponse.value = amountChargeImp.getBankCards(paymentMethodId)
    }

    fun getInstallments(amount: Int, paymentMethodId: String, idBank: String) = viewModelScope.launch {
        _installmentsResponse.value = Resource.Loading
        _installmentsResponse.value = amountChargeImp.getInstallments(amount, paymentMethodId, idBank)
    }
}