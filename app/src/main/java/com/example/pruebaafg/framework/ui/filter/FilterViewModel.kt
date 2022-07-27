package com.example.pruebaafg.framework.ui.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pruebaafg.data.model.PeriodEnum
import com.example.pruebaafg.data.model.TypeEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(): ViewModel()  {

    //LIVE DATA region
    private val _isMostShared = MutableLiveData<Boolean>()
    val isMostShared : LiveData<Boolean> get() = _isMostShared

    private val _typeSelected = MutableLiveData<TypeEnum>()
    val typeSelected : LiveData<TypeEnum> get() = _typeSelected

    private val _periodSelected = MutableLiveData<PeriodEnum>()
    val periodSelected : LiveData<PeriodEnum> get() = _periodSelected

    //VARIABLES
    private var sourceFacebookSelected :Boolean = false
    private var sourceTwitterSelected :Boolean = false
    private val _buttonEnabled = MutableLiveData<Boolean>()
    val buttonEnabled : LiveData<Boolean> get() = _buttonEnabled

    //INIT STATE
    fun onFragmentStarted() {
        _buttonEnabled.value = false
        _typeSelected.value = TypeEnum.NONE
        _periodSelected.value = PeriodEnum.NONE
        _isMostShared.value = false
        sourceTwitterSelected= false
        sourceFacebookSelected = false
    }

    //FEEDBACK
    fun typeSelected(type : TypeEnum) {
        _typeSelected.value = type
        _isMostShared.value = type.equals(TypeEnum.MOSTSHARED)
        if(type.equals(TypeEnum.MOSTSHARED)){
            sourceFacebookSelected = false
            sourceTwitterSelected = false
        }

        checkButtonState()
    }

    fun setButtonEnabled(enable : Boolean) {
        _buttonEnabled.value= enable
    }

    fun periodSelected(period : PeriodEnum) {
        _periodSelected.value = period
        checkButtonState()
    }

    fun checkboxSelected(selected : Boolean) {
        sourceFacebookSelected = selected
        checkButtonState()
    }

    fun checkbox2Selected(selected : Boolean) {
        sourceTwitterSelected = selected
        checkButtonState()
    }

    fun checkButtonState() {
        if(typeSelected.value!!.equals(TypeEnum.MOSTSHARED)) {
            if (periodSelected.value!!.equals(PeriodEnum.NONE)) {
                setButtonEnabled(false)
            } else {
                if(sourceFacebookSelected || sourceTwitterSelected) {
                    setButtonEnabled(true)
                } else {
                    setButtonEnabled(false)
                }
            }
        } else {
            if (!typeSelected.value!!.equals(TypeEnum.NONE) && !periodSelected.value!!.equals(
                    PeriodEnum.NONE
                )) {
                setButtonEnabled(true)
            } else {
                setButtonEnabled(false)
            }
        }
    }

}