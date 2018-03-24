package com.example.keshavanarasappa.androidproject.User

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.keshavanarasappa.androidproject.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener({
            val realm = RealmManager.instance.getRealm()
            val user = realm?.where(User::class.java)?.equalTo("emailId", emailEditText.text.toString())?.findFirst()
            if (user != null) {
                if (user.password == passwordEditText.text.toString()) {
                    setResults()
                } else {
                    Toast.makeText(this, "Error: Wrong password, try again", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Error: login failed, try again", Toast.LENGTH_LONG).show()
            }
        })

        registerButton.setOnClickListener({
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivityForResult(registerIntent,2)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 200) {
            setResults()
        }
    }

    private fun setResults() {
        val intent = Intent()
        intent.putExtra("login", true)
        setResult(200, intent)
        finish()
    }
}
