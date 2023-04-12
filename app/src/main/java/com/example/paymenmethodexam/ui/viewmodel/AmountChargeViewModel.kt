package com.example.paymenmethodexam.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paymenmethodexam.data.network.Resource
import com.example.paymenmethodexam.model.PaymentMethod
import com.example.paymenmethodexam.repository.AmountChargeImp
import com.example.paymenmethodexam.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AmountChargeViewModel @Inject constructor(
    private val amountChargeImp: AmountChargeImp
): ViewModel() {

    private val _loginResponse: MutableLiveData<Event<Resource<List<PaymentMethod>>>> = MutableLiveData()
    val loginResponse: LiveData<Event<Resource<List<PaymentMethod>>>>
        get() = _loginResponse

    fun getPaymentMethod() = viewModelScope.launch {

        _loginResponse.value = Event(amountChargeImp.getPaymentMethod())
    }
}