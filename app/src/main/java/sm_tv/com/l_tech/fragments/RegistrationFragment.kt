package sm_tv.com.l_tech.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import sm_tv.com.l_tech.R
import sm_tv.com.l_tech.util.MaskHelper.applyMask
import sm_tv.com.l_tech.util.MaskHelper.getNumberPhone
import sm_tv.com.l_tech.util.SharedPreferenceHelper.readInShared
import sm_tv.com.l_tech.util.SharedPreferenceHelper.saveInShared
import sm_tv.com.l_tech.util.SharedPreferenceHelper.saveMask
import sm_tv.com.l_tech.viewmodels.MainVM


class RegistrationFragment : Fragment() {
    private lateinit var edPhoneNumber: EditText
    private lateinit var edPassword: EditText
    private lateinit var buttonSignIn: Button
    private lateinit var viewModel: MainVM
    private var flag = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registration, container, false)

        init(view)

        validateShared()

        clickSignIn()

        observerResponseAuth()

        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    private fun init(view: View) {
        edPhoneNumber = view.findViewById(R.id.edPhoneNumber)
        edPassword = view.findViewById(R.id.edPassword)
        buttonSignIn = view.findViewById(R.id.buttonSignIn)
        viewModel = ViewModelProvider(this)[MainVM::class.java]
    }

    private fun clickSignIn() {
        buttonSignIn.setOnClickListener {
            val currentPhone = edPhoneNumber.text.toString()
            if (chekInput(currentPhone)) {
                val phone = getNumberPhone(currentPhone)
                val password = edPassword.text.toString()
                signIn(phone, password)
            } else {
                edPhoneNumber.error = "Поле не должно быть пустым"
            }
        }
    }

    private fun displaysNumberAccordingMask() {
        viewModel.getMaskFromServer()
        viewModel.phoneMask.observe(viewLifecycleOwner) { mask ->
            saveMask(mask, requireContext())
            edPhoneNumber.hint = "Phone number $mask"
            applyMask(mask, edPhoneNumber)
        }
    }

    private fun signIn(phone: String, password: String) {
        viewModel.signIn(phone, password)
    }

    private fun observerResponseAuth(){
        viewModel.responseAuth.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                saveInShared(
                    phone = edPhoneNumber.text.toString(),
                    password = edPassword.text.toString(),
                    context = requireContext()
                )
                findNavController().navigate(R.id.action_registrationFragment_to_contentFragment)
            } else {
                Toast
                    .makeText(
                        requireContext(),
                        "Такой пользователь не найден ${edPhoneNumber.text}, ${edPassword.text} ",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        })
    }

    private fun validateShared() {
        val userData = readInShared(requireContext())
        if (userData != null) {
            edPhoneNumber.setText(userData.phone)
            edPassword.setText(userData.password)
            applyMask(userData.currentMask, edPhoneNumber)
        } else {
            displaysNumberAccordingMask()
        }
    }

    private fun chekInput(title: String): Boolean {
        return !(TextUtils.isEmpty(title))
    }
}
