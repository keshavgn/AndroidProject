package com.example.keshavanarasappa.androidproject.user

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.widget.Toast
import com.example.keshavanarasappa.androidproject.R
import com.example.keshavanarasappa.androidproject.common.BaseActivity
import com.google.android.gms.auth.api.credentials.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResolvableApiException
import kotlinx.android.synthetic.main.activity_login.*








class LoginActivity : BaseActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private lateinit var mCredentialsClient: CredentialsClient
    private lateinit var mCurrentCredential: Credential
    private var mIsResolving = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val option = CredentialsOptions.Builder().forceEnableSaveDialog().build()
        mCredentialsClient = Credentials.getClient(this, option)

        loginButton.setOnClickListener {
            val realm = RealmManager.instance.getRealm()
            val user = realm?.where(User::class.java)?.equalTo(EMAIL_ID, emailEditText.text.toString())?.findFirst()
            if (user != null) {
                if (user.password == passwordEditText.text.toString()) {
                    saveCredentials(username = user.emailId, password = user.password)
                } else {
                    Toast.makeText(this, PASSWORD_FAIL, Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, LOGIN_FAIL, Toast.LENGTH_LONG).show()
            }
        }

        registerButton.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivityForResult(registerIntent,2)
        }
    }

    public override fun onStart() {
        super.onStart()

        // Attempt auto-sign in.
        if (!mIsResolving) {
            requestCredentials()
        }
    }

    private fun saveCredentials(username: String, password: String) {
        val credential = Credential.Builder(username).setPassword(password).build()
        mCredentialsClient.save(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                showToast("Credential saved.")
            } else {
                val exception = it.exception as ResolvableApiException
                resolveResult(exception, 1)
            }
        }
    }

    private fun resolveResult(rae: ResolvableApiException, requestCode: Int) {
        if (mIsResolving) {
            return
        }
        try {
            rae.startResolutionForResult(this, requestCode)
            mIsResolving = true
        } catch (e: IntentSender.SendIntentException) {
            Toast.makeText(this, "STATUS: Failed to send resolution.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            200 -> {
                setResults()
            }
            1 -> {
                if (resultCode == Activity.RESULT_OK) {
                    setResults()
                    showToast("Credential Save Success")
                } else {
                    showToast("Credential Save Failed")
                }
                mIsResolving = false
            }
            2 -> {
                val credential = data?.getParcelableExtra<Credential>(Credential.EXTRA_KEY)
                credential?.let { processRetrievedCredential(it, false) }
                mIsResolving = false
            }
        }
    }

    private fun setResults() {
        val intent = Intent()
        intent.putExtra(LOGIN, true)
        setResult(200, intent)
        finish()
    }

    private fun requestCredentials() {
        val request = CredentialRequest.Builder()
                .setPasswordLoginSupported(true)
                .setAccountTypes(IdentityProviders.GOOGLE)
                .build()

        mCredentialsClient.request(request).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        processRetrievedCredential(task.result.credential, false)
                    } else {
                        val rae = task.exception
                        if (rae is ResolvableApiException) {
                            resolveResult(rae, 2)
                        }
                    }
                }
    }

    private fun processRetrievedCredential(credential: Credential, isHint: Boolean) {
        if (!isHint) {
            showToast("Credential Retrieved")
            mCurrentCredential = credential
        } else {
            showToast("Credential Hint Retrieved")
        }

        emailEditText.setText(credential.id)
        passwordEditText.setText(credential.password)
    }

    companion object {
        private const val LOGIN = "login"
        private const val EMAIL_ID = "emailId"
        private const val LOGIN_FAIL = "Error: login failed, try again"
        private const val PASSWORD_FAIL = "Error: Wrong password, try again"
    }

    override fun onConnected(p0: Bundle?) {
         //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
         //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionSuspended(p0: Int) {
         //To change body of created functions use File | Settings | File Templates.
    }
}
