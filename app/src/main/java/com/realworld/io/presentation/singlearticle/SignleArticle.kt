package com.realworld.io.presentation.singlearticle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.navArgs
import com.realworld.io.R
import com.realworld.io.databinding.FragmentSignleArticleBinding
import com.realworld.io.presentation.editarticle.EditFragmentArgs

class SignleArticle : Fragment() {

    private  var _binding: FragmentSignleArticleBinding?= null
    private val binding get() = _binding!!
    val args: SignleArticleArgs by navArgs()

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

        binding.apply{
            args.singleArticle.let {
                title.text =   it.title
                desc.text = it.description
                shortDesc.text = it.body
                userName.text = it.username
                date.text = it.createdAt.toString()
                category.text = it.category
            }

        }

        super.onViewCreated(view, savedInstanceState)
    }

}