package com.hackerton.noahah.presentation.ui.service.hear

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.hackerton.noahah.R
import com.hackerton.noahah.databinding.FragmentHearBinding
import com.hackerton.noahah.presentation.base.BaseFragment
import com.hackerton.noahah.presentation.ui.service.ServiceViewModel
import com.hackerton.noahah.presentation.util.Constants.BRAILLE
import com.hackerton.noahah.presentation.util.Constants.HEAR
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HearFragment : BaseFragment<FragmentHearBinding>(R.layout.fragment_hear) {

    private val parentViewModel: ServiceViewModel by activityViewModels()
    private val viewModel: HearViewModel by viewModels()
    private val args: HearFragmentArgs by navArgs()
    private val type by lazy { args.type }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        viewModel.setPdfId(parentViewModel.getPdfId())
        viewModel.setDataType(type)
    }

}