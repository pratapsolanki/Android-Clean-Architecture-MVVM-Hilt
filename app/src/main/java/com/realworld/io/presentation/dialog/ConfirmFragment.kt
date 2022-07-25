package com.realworld.io.presentation.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.realworld.io.databinding.FragmentConfirmBinding
import com.realworld.io.databinding.FragmentSignleArticleBinding
import com.realworld.io.presentation.editarticle.EditFragmentArgs

class ConfirmFragment : BottomSheetDialogFragment() {

    private  var _binding: FragmentConfirmBinding?= null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConfirmBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}