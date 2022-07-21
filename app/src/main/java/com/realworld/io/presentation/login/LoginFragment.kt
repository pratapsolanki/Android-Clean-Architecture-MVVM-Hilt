package com.realworld.io.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.realworld.io.util.Logger
import com.realworld.io.R
import com.realworld.io.util.Resource
import com.realworld.io.util.TokenManager
import com.realworld.io.databinding.FragmentLoginBinding
import com.realworld.io.model.LoginInput
import com.realworld.io.model.User
import com.realworld.io.util.gone
import com.realworld.io.util.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val viewModel : LoginViewModel by viewModels()
    private  var _binding: FragmentLoginBinding?= null
    private val binding get() = _binding!!
    @Inject lateinit var  tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        if(::tokenManager.isInitialized){
             if (tokenManager.getToken() != null){
                findNavController().navigate(R.id.action_loginFragment_to_dashBaord)
            }
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener {
            val validationResult = checkValidation()
            if (validationResult){
                val loginInput = LoginInput(User("${getUserRequest().email}","${getUserRequest().password}"))
                viewModel.login(loginInput)
            }
        }

        binding.signInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
        bindObserver()
    }

    private fun getUserRequest() : User{
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        return User(email , password)
    }

    private fun checkValidation(): Boolean {
        var returnValue = true
        if (binding.edtEmail.text.toString().isBlank()) {
            binding.inputEmail.error = "Email Should not be Blank"
            returnValue = false
        } else binding.inputEmail.error = null

        if (binding.edtPassword.text.toString().isBlank()) {
            binding.inputPassword.error = "Password Should not be Blank"
            returnValue = false
        } else binding.inputPassword.error = null

        if (binding.edtPassword.text.toString().trim().length  <= 7) {
            binding.inputPassword.error = "Password Should have 8 or longer"
            returnValue = false
        } else binding.inputPassword.error = null

        return returnValue
    }

    private fun bindObserver() {
        binding.progressBar.gone()
        viewModel.loginResponseLiveData2.observe(requireActivity(), Observer {
            when (it) {
                is Resource.Success -> {
                    Logger.d(it.data.toString())
                    binding.progressBar.gone()
                    tokenManager.saveToken(it.data!!.user.token,it.data!!.user.username)
                    findNavController().navigate(R.id.action_loginFragment_to_dashBaord)
                }
                is Resource.Error -> {
                    binding.errorText.text = it.errorMessage
                    binding.progressBar.gone()
                }
                is Resource.Loading -> {
                    binding.progressBar.visible()
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}