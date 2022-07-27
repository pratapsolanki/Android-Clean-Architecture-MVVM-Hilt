package com.realworld.io.presentation.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.realworld.io.databinding.FragmentConfirmBinding

class ConfirmFragment : DialogFragment() {

    private  var _binding: FragmentConfirmBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {
        const val TAG = "PurchaseConfirmationDialog"
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}