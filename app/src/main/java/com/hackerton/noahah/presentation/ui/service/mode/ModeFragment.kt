package com.hackerton.noahah.presentation.ui.service.mode

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.hackerton.noahah.R
import com.hackerton.noahah.databinding.FragmentModeBinding
import com.hackerton.noahah.presentation.base.BaseFragment

class ModeFragment: BaseFragment<FragmentModeBinding>(R.layout.fragment_mode) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnToHear.setOnClickListener {
            findNavController().toHearFragment()
        }
    }

    private fun NavController.toHearFragment(){
        val action = ModeFragmentDirections.actionModeFragmentToHearFragment()
        navigate(action)
    }
}