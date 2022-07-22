package com.realworld.io.presentation.signup

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
import com.realworld.io.databinding.FragmentSignupBinding
import com.realworld.io.domain.model.SignUpInput
import com.realworld.io.domain.model.UserCommon
import com.realworld.io.util.gone
import com.realworld.io.util.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private val viewModel: SignupViewModel by viewModels()
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        if (::tokenManager.isInitialized) {
            if (tokenManager.getToken() != null) {
                findNavController().navigate(R.id.action_loginFragment_to_dashBaord)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUpBtn.setOnClickListener {
            val validationResult = checkValidation()
            if (validationResult) {
                val signUpInput = SignUpInput(
                    UserCommon(
                        "${getUserRequest().email}",
                        "${getUserRequest().password}",
                        "${getUserRequest().username}"
                    )
                )
                viewModel.signup(signUpInput)
            }
        }

        binding.loginBtnText.setOnClickListener {
            findNavController().popBackStack()
        }
        bindObserver()
    }

    private fun getUserRequest(): UserCommon {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        val username = binding.edtUsername.text.toString().trim()
        return UserCommon(email, password, username)
    }

    private fun checkValidation(): Boolean {
        var returnValue = true
        if (binding.edtEmail.text.toString().isBlank()) {
            binding.inputEmail.error = "Email Should not be Blank"
            returnValue = false
        } else binding.inputEmail.error = null

        if (binding.edtUsername.text.toString().isBlank()) {
            binding.inputUsername.error = "Username Should not be Blank"
            returnValue = false
        } else binding.inputUsername.error = null

        if (binding.edtPassword.text.toString().isBlank()) {
            binding.inputPassword.error = "Password Should not be Blank"
            returnValue = false
        } else binding.inputPassword.error = null

        if (binding.edtPassword.text.toString().trim().length <= 7) {
            binding.inputPassword.error = "Password Should have 8 or longer"
            returnValue = false
        } else binding.inputPassword.error = null

        return returnValue
    }

    private fun bindObserver() {
        binding.progressBar.gone()
        viewModel.loginResponseLiveData.observe(requireActivity(), Observer {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.gone()
                    Logger.d(it.data.toString())
                    tokenManager.saveToken(it.data!!.user.token,it.data!!.user.username)
                    findNavController().navigate(R.id.action_signupFragment_to_dashBaord)
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