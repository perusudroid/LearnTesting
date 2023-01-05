package com.androidsolution.learntesting.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.androidsolution.learntesting.R
import com.androidsolution.learntesting.common.Status
import com.androidsolution.learntesting.vm.ShoppingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddShoppingItemFragment : Fragment(R.layout.fragment_add_shopping) {

    private val shoppingViewModel: ShoppingViewModel by viewModels()
    private var prodName: AppCompatEditText? = null
    private var label: AppCompatTextView? = null
    private var seekBar: SeekBar? = null
    private var btnAdd: AppCompatButton? = null
    private var etPrice: AppCompatEditText? = null
    private var amount = 0
    private var lblStatus: AppCompatTextView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setAssets()
        subscribeToObservers()
    }

    private fun bindViews(view: View) {
        label = view.findViewById(R.id.lblValue)
        prodName = view.findViewById(R.id.etProdName)
        seekBar = view.findViewById(R.id.seekBar)
        btnAdd = view.findViewById(R.id.btnAdd)
        etPrice = view.findViewById(R.id.etPrice)
        lblStatus = view.findViewById(R.id.lblStatus)
    }

    private fun setAssets() {
        btnAdd?.setOnClickListener {
            /*shoppingViewModel.insertShoppingItem(
                prodName?.text?.toString(),
                amount.toString(),
                etPrice?.text.toString()
            )*/
            lblStatus?.text = "Added New Record"
        }
        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, value: Int, p2: Boolean) {
                amount = value
                label?.text = value.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
    }

    private fun subscribeToObservers() {
        shoppingViewModel.insertShoppingItemStatus.observe(viewLifecycleOwner) {
            Log.d(
                "AddShoppingItem",
                "subscribeToObservers: status ${it.status} is null ${lblStatus == null}"
            )
            when (it.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    lblStatus?.text = "Added New Record"
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}