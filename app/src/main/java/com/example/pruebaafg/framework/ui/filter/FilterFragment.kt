package com.example.pruebaafg.framework.ui.filter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pruebaafg.R
import com.example.pruebaafg.data.model.PeriodEnum
import com.example.pruebaafg.data.model.TypeEnum
import com.example.pruebaafg.databinding.FragmentFilterBinding

class FilterFragment : Fragment() {

    private lateinit var binding: FragmentFilterBinding
    private val viewModel by viewModels<FilterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun setButtonEnabled(enabled : Boolean) {
        with(binding) {
            //button.isEnabled = enabled
            button.isClickable = enabled
            if (enabled) {
                button.setAlpha(1f)
            } else {
                button.setAlpha(0.3f)
            }
        }
    }

    fun enableSharedOptions(enabled : Boolean) {
        with(binding) {
            checkBox.setChecked(false)
            checkBox.setEnabled(enabled)
            checkBox2.setChecked(false)
            checkBox2.setEnabled(enabled)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.app_name)
        viewModel.onFragmentStarted()

        var type: TypeEnum = TypeEnum.NONE
        var period : PeriodEnum = PeriodEnum.NONE
        var sharedFacebook : Boolean = false;
        var sharedTwitter : Boolean = false;

        viewModel.buttonEnabled.observe(getViewLifecycleOwner()) { enabled ->
            setButtonEnabled(enabled)
        }

        viewModel.isMostShared.observe(getViewLifecycleOwner()) { isMostShared ->
            enableSharedOptions(isMostShared)
        }

        with(binding) {
            filter1.setOnCheckedChangeListener { group, checkedId ->
                type = when (checkedId) {
                    R.id.radio_mostmailed -> TypeEnum.MOSTMAILED
                    R.id.radio_mostshared -> TypeEnum.MOSTSHARED
                    R.id.radio_mostviewed -> TypeEnum.MOSTVIEWED
                    else -> TypeEnum.NONE
                }
                viewModel.typeSelected(type)
            }

            filter2.setOnCheckedChangeListener { group, checkedId ->
                period = when (checkedId) {
                    R.id.radio_one_day -> PeriodEnum.ONE_DAY
                    R.id.radio_seven_days -> PeriodEnum.SEVEN_DAYS
                    R.id.radio_thirty_days -> PeriodEnum.THIRTY_DAYS
                    else -> PeriodEnum.NONE
                }
                viewModel.periodSelected(period)
            }

            checkBox.setOnClickListener{
                sharedFacebook = checkBox.isChecked
                viewModel.checkboxSelected(checkBox.isChecked)
            }

            checkBox2.setOnClickListener{
                sharedTwitter = checkBox2.isChecked
                viewModel.checkbox2Selected(checkBox2.isChecked)
            }

            button.setOnClickListener {
                val dir = FilterFragmentDirections.actionFilterFragmentToListFragment(
                    sharedFacebook,
                    sharedTwitter,
                    period,
                    type
                )
                findNavController().navigate(dir)
            }

        }
    }

}