package com.realworld.io.presentation.singlearticle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.realworld.io.databinding.FragmentSignleArticleBinding

class SingleArticle : Fragment() {

    private  var _binding: FragmentSignleArticleBinding?= null
    private val binding get() = _binding!!
    private val args: SingleArticleArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignleArticleBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply{
            args.singleArticle.let {
                category.text = it.tagList.toString()
                title.text =   it.title
                desc.text = it.description
                shortDesc.text = it.body
                date.text = it.createdAt
                userName.text = it.author.username
            }

        }

        super.onViewCreated(view, savedInstanceState)
    }
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}