package com.example.bancom.feature.auth

import android.app.Activity
import android.content.IntentSender
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.example.bancom.BuildConfig
import com.example.bancom.R
import com.example.bancom.base.BaseFragment
import com.example.bancom.databinding.FragmentLoginBinding
import com.example.domain.entity.AuthUser
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    private lateinit var auth: FirebaseAuth
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var startActivityForResult: ActivityResultLauncher<IntentSenderRequest>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentLoginBinding.inflate(layoutInflater)

        init()
        initView()
        initViewModel()
        initViewObserver()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.login_title_bar)
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    private fun init() {
        oneTapClient = Identity.getSignInClient(requireActivity())
        signInRequest = BeginSignInRequest.builder().setPasswordRequestOptions(
            BeginSignInRequest.PasswordRequestOptions.builder().setSupported(true).build()
        ).setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder().setSupported(true)
                // Your server's client ID, not your Android client ID.
                .setServerClientId(BuildConfig.GOOGLE_ID)
                // Only show accounts previously used to sign in.
                .setFilterByAuthorizedAccounts(true).build()
        )
            // Automatically sign in when exactly one credential is retrieved.
            .setAutoSelectEnabled(true).build()
    }

    override fun initView() {
        binding.btnLogin.setOnClickListener {
            loginWithEmail()
        }

        binding.btnLoginGoogle.setOnClickListener {
            loginWithGoogle()
        }
    }

    private fun loginWithEmail() {
        hideKeyBoard(binding.btnLogin)

        if (binding.edtEmail.text.isNullOrEmpty()) {
            showMessageSnack(binding.root, getString(R.string.login_email_required))
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(binding.edtEmail.text.toString()).matches()) {
            showMessageSnack(binding.root, getString(R.string.login_email_format))
            return
        }

        if (binding.edtPassword.text.isNullOrEmpty()) {
            showMessageSnack(binding.root, getString(R.string.login_password_required))
            return
        }

        var email = ""

        if (binding.chkSaveEmail.isChecked) {
            email = "andre-gnr@hotmail.com"
        }

        viewModel.saveAuth(AuthUser("", "", email))
    }

    private fun loginWithGoogle() {
        hideKeyBoard(binding.btnLoginGoogle)

        oneTapClient.beginSignIn(signInRequest).addOnSuccessListener { result ->
            try {
                startActivityForResult.launch(
                    IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                )
            } catch (e: IntentSender.SendIntentException) {
                showMessageSnack(
                    binding.root, "Couldn't start One Tap UI: ${e.localizedMessage}"
                )
            }
        }.addOnFailureListener {
            // No saved credentials found. Launch the One Tap sign-up flow, or
            // do nothing and continue presenting the signed-out UI.
            showMessageSnack(binding.root, it.localizedMessage.orEmpty())
        }
    }

    override fun initViewModel() {
        auth = Firebase.auth
        viewModel.getAuth()
    }

    override fun initViewObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.authUser.collect {
                    when (it) {
                        is LoginUIState.SuccessAuth -> {
                            hideProgress()
                            binding.edtEmail.setText(it.data.email)
                        }

                        is LoginUIState.Error -> {
                            hideProgress()
                            processError(binding.root, it.exception)
                        }

                        else -> {
                            hideProgress()
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.authLogin.collect {
                    when (it) {
                        is LoginUIState.SuccessAuth -> {
                            hideProgress()
                            goToHome()
                        }

                        is LoginUIState.Error -> {
                            hideProgress()
                            processError(binding.root, it.exception)
                        }

                        else -> {
                            hideProgress()
                        }
                    }
                }
            }
        }

        initActivityForResult()
    }

    private fun initActivityForResult() {
        startActivityForResult = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result: ActivityResult ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    val googleCredential = oneTapClient.getSignInCredentialFromIntent(result.data)
                    val idToken = googleCredential.googleIdToken

                    when {
                        idToken != null -> {
                            // Got an ID token from Google. Use it to authenticate
                            // with Firebase.
                            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                            auth.signInWithCredential(firebaseCredential)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // Sign in success, update UI with the signed-in user's information
                                        val user = auth.currentUser
                                        var email = ""

                                        if (binding.chkSaveEmail.isChecked) {
                                            email = user?.email.orEmpty()
                                        }

                                        viewModel.saveAuth(AuthUser("", "", email))
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        showMessageSnack(
                                            binding.root,
                                            "signInWithCredential:failure.\" + \" (${task.exception?.localizedMessage.orEmpty()})"
                                        )
                                    }
                                }
                        }

                        else -> {
                            // Shouldn't happen.
                            showMessageSnack(binding.root, "No ID token!")
                        }
                    }
                }
            }
        }
    }

    private fun goToHome() {
        val navController = Navigation.findNavController(binding.root)
        navController.navigate(R.id.homeFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}