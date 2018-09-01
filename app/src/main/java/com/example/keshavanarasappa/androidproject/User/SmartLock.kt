package com.example.keshavanarasappa.androidproject.user

import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Status

class SmartLock {
    private lateinit var mGoogleApiClient: GoogleApiClient
//    var saveCredentialsListener: SaveCredentials? = null
    var mIsResolving = false
    lateinit var context: LoginActivity

    interface SaveCredentials {
        fun saveCredentials(message: String)
        fun goToContent()
    }

    fun setupGoogleApiClient() {
//        saveCredentialsListener = context
        this.context = context
        mGoogleApiClient = GoogleApiClient.Builder(context)
                .addConnectionCallbacks(context)
                .enableAutoManage(context, 0, context)
                .addApi(Auth.CREDENTIALS_API)
                .build()
    }

    fun saveCredentials(username: String, password: String) {
        val credential = Credential.Builder(username).setPassword(password).build()
        if (credential.id == username && credential.password == password) {
            saveCredentials(credential)
        } else {
//            saveCredentialsListener?.saveCredentials("Credential failed to save")
        }
    }

    private fun saveCredentials(credential: Credential) {
        Auth.CredentialsApi.save(mGoogleApiClient, credential).setResultCallback {
            if (it.isSuccess) {
//                saveCredentialsListener?.saveCredentials("Credential saved")
            } else {
//                saveCredentialsListener?.saveCredentials("Failed: " + it.statusMessage + " Code: ("+ it.statusCode + ")")
                resolveResult(it, 1)
            }
        }
    }

    private fun resolveResult(status: Status, requestCode: Int) {
        if (mIsResolving) {
            return
        }

        if (status.hasResolution()) {
            status.startResolutionForResult(context, requestCode)
            mIsResolving = true
        } else {
            if (requestCode == 1) {
//                saveCredentialsListener?.goToContent()
            }
        }
    }

}