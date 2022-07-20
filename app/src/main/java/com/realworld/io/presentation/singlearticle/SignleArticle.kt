package com.realworld.io.presentation.singlearticle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.realworld.io.databinding.FragmentSignleArticleBinding

class SignleArticle : Fragment() {

    private  var _binding: FragmentSignleArticleBinding?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignleArticleBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.title.text = arguments?.getString("title")
        binding.body.text = arguments?.getString("body")
        super.onViewCreated(view, savedInstanceState)
    }

}