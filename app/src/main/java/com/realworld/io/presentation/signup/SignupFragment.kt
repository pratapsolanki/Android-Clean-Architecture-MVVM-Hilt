package com.realworld.io.presentation.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.realworld.io.R
import com.realworld.io.databinding.FragmentSignupBinding
import com.realworld.io.domain.model.SignUpInput
import com.realworld.io.domain.model.UserCommon
import com.realworld.io.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private val viewModel: SignupViewModel by viewModels()
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        onClickListener()
        bindObserver()
        binding.progressBar.gone()
    }

    private fun onClickListener() {
        binding.signUpBtn.setOnClickListener {
            if (!requireActivity().isNetworkAvailable()){
                requireActivity().toast("No Internet Connected")
            }else{
                val signUpInput = SignUpInput(
                    UserCommon(
                        getUserRequest().email,
                        getUserRequest().password,
                        getUserRequest().username
                    )
                )
                viewModel.signup(signUpInput)
                validation()
            }
        }

        binding.loginBtnText.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }
    }

    private fun validation() {
        viewModel.state.email = getUserRequest().email
        binding.inputEmail.error = viewModel.state.emailError

        viewModel.state.password = getUserRequest().password
        binding.inputPassword.error = viewModel.state.passwordError

        viewModel.state.username = getUserRequest().username
        binding.inputUsername.error = viewModel.state.usernameError

    }


    private fun getUserRequest(): UserCommon {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        val username = binding.edtUsername.text.toString().trim()
        return UserCommon(email, password, username)
    }


    private fun bindObserver() {
        binding.progressBar.gone()
        lifecycleScope.launchWhenCreated {
            viewModel.signUpUIState.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        binding.progressBar.gone()
                        tokenManager.saveToken(it.data!!.user.token, it.data.user.username)
                        findNavController().navigate(R.id.action_signupFragment_to_dashBaord)
                    }
                    is Resource.Error -> {
                        binding.errorText.text = it.errorMessage
                        binding.progressBar.gone()
                    }
                    is Resource.Loading -> {
                        Logger.d("Loading")
                        binding.progressBar.visible()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


}