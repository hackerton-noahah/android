package com.hackerton.noahah.presentation.ui.service.hear

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.hackerton.noahah.R
import com.hackerton.noahah.databinding.FragmentHearBinding
import com.hackerton.noahah.presentation.base.BaseFragment
import com.hackerton.noahah.presentation.ui.service.ServiceViewModel

class HearFragment: BaseFragment<FragmentHearBinding>(R.layout.fragment_hear) {

    private val parentViewModel: ServiceViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}