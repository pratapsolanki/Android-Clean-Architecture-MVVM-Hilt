package com.realworld.io.presentation.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.realworld.io.R
import com.realworld.io.databinding.FragmentLoginBinding
import com.realworld.io.domain.model.LoginInput
import com.realworld.io.domain.model.User
import com.realworld.io.domain.use_cases.RegistrationFormState
import com.realworld.io.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val viewModel : LoginViewModel by viewModels()
    private  var _binding: FragmentLoginBinding?= null
    private val binding get() = _binding!!
    @Inject lateinit var  tokenManager: TokenManager



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        bindObserver()
        binding.progressBar.gone()
//        binding.loginBtn.setOnClickListener {
//            if (requireActivity().isNetworkAvailable()){
//                val validationResult = checkValidation()
//                    if (validationResult) {
//                        val loginInput = LoginInput(User(getUserRequest().email, getUserRequest().password))
//                        viewModel.loginWithState(loginInput)
//                    }
//            }else {
//                requireActivity().toast("No Internet Connected")
//            }
//        }

        binding.loginBtn.setOnClickListener {

            viewModel.state.email = getUserRequest().email
            binding.inputEmail.error = viewModel.state.emailError

            Logger.d(viewModel.state.emailError.toString())

            viewModel.state.password = getUserRequest().password
            binding.inputPassword.error = viewModel.state.passwordError

            Logger.d(viewModel.state.passwordError.toString())

            viewModel.loginWithState(LoginInput(User(getUserRequest().email,getUserRequest().password)))

        }

        binding.signInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }




    private fun bindObserver() {
        lifecycleScope.launchWhenCreated {
            viewModel.loginUIState.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        Logger.d("Success main")
                        binding.progressBar.gone()
                        findNavController().navigate(R.id.action_loginFragment_to_dashBaord)
                        tokenManager.saveToken(it.data!!.user.token,it.data.user.username)
                        requireContext().toast("Success")
                    }
                    is Resource.Error -> {
                        binding.errorText.text = it.errorMessage
                        binding.progressBar.gone()
                    }
                    is Resource.Loading -> {
                        Logger.d("Loader main")
                        binding.progressBar.visible()
                    }
                }

            }
        }


//        lifecycleScope.launchWhenCreated {
//            viewModel.loginUIState.collectLatest {
//                when (it) {
//                    is Resource.Success -> {
//                        binding.progressBar.gone()
//                        tokenManager.saveToken(it.data!!.user.token,it.data.user.username)
//                        findNavController().navigate(R.id.action_loginFragment_to_dashBaord)
//                    }
//                    is Resource.Error -> {
//                        binding.errorText.text = it.errorMessage
//                        binding.progressBar.gone()
//                    }
//                    is Resource.Loading -> {
//                        binding.progressBar.visible()
//                    }
//                }
//            }
//        }


    }

    private fun getUserRequest() : User {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        return User(email , password)
    }



    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


}