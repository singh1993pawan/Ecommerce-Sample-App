package com.freeelective.ecommercedemo.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.freeelective.ecommercedemo.R
import com.freeelective.ecommercedemo.api.*
import com.freeelective.ecommercedemo.data.model.LoginRequestData
import com.freeelective.ecommercedemo.databinding.FragmentLoginBinding
import com.freeelective.ecommercedemo.factory.DataViewModelFactory
import com.freeelective.ecommercedemo.repository.LoginRepository
import com.freeelective.ecommercedemo.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel:LoginViewModel
    private lateinit var dataRepository: LoginRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiService = RetrofitInstance.apiService
        dataRepository = LoginRepository(apiService)
        loginViewModel = ViewModelProvider(this, DataViewModelFactory(dataRepository)).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        val view = binding.root
        requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).visibility=View.GONE

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       //(activity as AppCompatActivity?)?.supportActionBar?.hide()

        val emailText = binding.outlinedEmailTextField.editText?.text.toString()
        val passwordText = binding.outlinedPasswordTextField.editText?.text.toString()
        binding.logInButton.setOnClickListener {
            binding.progressbar.visibility=View.VISIBLE
            loginViewModel.fetchData(LoginRequestData(emailText,passwordText))
        }

        loginViewModel.data.observe(viewLifecycleOwner, Observer {

            when(it){
                is ApiResponse.Success->{
                    findNavController().navigate(R.id.action_loginFragment_to_homePageFragment2)
                    binding.progressbar.visibility=View.GONE
                }
                is ApiResponse.Error->{
                    Log.e("Error",it.error)
                }
            }

        })
    }


}