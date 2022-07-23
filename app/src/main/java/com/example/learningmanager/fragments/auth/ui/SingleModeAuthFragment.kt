package com.example.learningmanager.fragments.auth.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.learningmanager.BuildConfig
import com.example.learningmanager.base.ui.BaseFragment
import com.example.learningmanager.databinding.FragmentSingleModeAuthBinding
import com.example.learningmanager.fragments.myinspiration.FirebaseManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val REQUEST_CODE_SIGN_IN = 1001

@AndroidEntryPoint
class SingleModeAuthFragment @Inject constructor() :
    BaseFragment<FragmentSingleModeAuthBinding, SingleModeAuthViewModel>(
        FragmentSingleModeAuthBinding::inflate
    ) {
    override val vm: SingleModeAuthViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFirebaseAuth()
        onGoogleSignClick()
    }

    private fun initFirebaseAuth() {
        auth = FirebaseAuth.getInstance()
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.webclient_id)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), options)
    }

    private fun onGoogleSignClick() {
        layout.btnGoogleSignIn.setOnClickListener {
//            vm.navigateToViewPagerFragment()
            googleSignInClient.signInIntent.also {
                startActivityForResult(it, REQUEST_CODE_SIGN_IN)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
            account?.let {
                googleAuthForFirebase(it, it.email!!, it.displayName!!, it.photoUrl.toString()!!)
            }

        }
    }

    private fun googleAuthForFirebase(
        account: GoogleSignInAccount,
        userEmail: String,
        displayName: String,
        photoUrl: String
    ) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithCredential(credentials)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Successfully logged in", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        vm.navigateToViewPagerFragment(userEmail, displayName, photoUrl)
    }
}