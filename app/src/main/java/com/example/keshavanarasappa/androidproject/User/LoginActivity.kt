package com.example.keshavanarasappa.androidproject.user

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.keshavanarasappa.androidproject.R
import com.example.keshavanarasappa.androidproject.common.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener {
            val realm = RealmManager.instance.getRealm()
            val user = realm?.where(User::class.java)?.equalTo(EMAIL_ID, emailEditText.text.toString())?.findFirst()
            if (user != null) {
                if (user.password == passwordEditText.text.toString()) {
                    setResults()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 200) {
            setResults()
        }
    }

    private fun setResults() {
        val intent = Intent()
        intent.putExtra(LOGIN, true)
        setResult(200, intent)
        finish()
    }

    companion object {
        private const val LOGIN = "login"
        private const val EMAIL_ID = "emailId"
        private const val LOGIN_FAIL = "Error: login failed, try again"
        private const val PASSWORD_FAIL = "Error: Wrong password, try again"
    }
}
