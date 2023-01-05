package com.androidsolution.learntesting.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.androidsolution.learntesting.R
import com.androidsolution.learntesting.vm.ShoppingViewModel

class ShoppingFragment : Fragment(R.layout.fragment_shopping) {

    private lateinit var shoppingViewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shoppingViewModel = ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
    }
}