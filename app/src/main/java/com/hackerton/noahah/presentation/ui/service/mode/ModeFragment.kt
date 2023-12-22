package com.hackerton.noahah.presentation.ui.service.mode

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.hackerton.noahah.R
import com.hackerton.noahah.databinding.FragmentModeBinding
import com.hackerton.noahah.presentation.base.BaseFragment
import com.hackerton.noahah.presentation.ui.service.ServiceViewModel
import com.hackerton.noahah.presentation.util.Constants.BRAILLE
import com.hackerton.noahah.presentation.util.Constants.HEAR
import dagger.hilt.android.AndroidEntryPoint

class ModeFragment: BaseFragment<FragmentModeBinding>(R.layout.fragment_mode) {

    private val parentViewModel: ServiceViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserverType()

        binding.btnToHear.setOnClickListener {
            parentViewModel.buttonClicked()
            findNavController().toHearFragment(HEAR)
        }

        binding.btnToBraille.setOnClickListener {
            parentViewModel.buttonClicked()
            findNavController().toHearFragment(BRAILLE)
        }
    }

    private fun initObserverType(){
        repeatOnStarted {
            parentViewModel.type.collect{
                when(it){
                    BRAILLE -> findNavController().toHearFragment(BRAILLE)
                    HEAR -> findNavController().toHearFragment(HEAR)
                }
            }
        }
    }

    private fun NavController.toHearFragment(type: String){
        val action = ModeFragmentDirections.actionModeFragmentToHearFragment(type)
        navigate(action)
    }
}