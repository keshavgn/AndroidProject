package com.example.keshavanarasappa.androidproject.User

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.keshavanarasappa.androidproject.R
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var calender = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calender.set(Calendar.YEAR, year)
            calender.set(Calendar.MONTH, monthOfYear)
            calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)
            dateEditText.text = dateFormat.format(calender.time)
        }

        dateEditText.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                    calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH),
                    calender.get(Calendar.DAY_OF_MONTH)).show()
        }

        registerButton.setOnClickListener({
            val realm = RealmManager.instance.getRealm()
            var errorMessage: String? = null
            if (!emailValidator(nameEditText.text.toString())) {
                errorMessage = "Email address is invalid"
            } else if (passwordEditText.text.length < 4) {
                errorMessage = "Password is too short"
            } else if (realm?.where(User::class.java)?.equalTo("emailId", nameEditText.text.toString())?.findFirst() != null) {
                errorMessage = "Email address already exists!, Choose different"
            }

            if (errorMessage != null) {
                Toast.makeText(this, "Error: ".plus(errorMessage), Toast.LENGTH_LONG).show()
            } else {
                realm?.executeTransaction({
                    val user = realm.createObject(User::class.java, nameEditText.text.toString())
                    user.password = passwordEditText.text.toString()
                    user.dob = dateEditText.text.toString()
                    user.sex = if (maleSelected() == true) "Male" else "Female"
                    user.status = statusButton.isChecked
                    finish()
                })
            }
        })
    }

    private fun emailValidator(email: String): Boolean {
        val emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val pattern = Pattern.compile(emailPattern)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    private fun maleSelected(): Boolean {
        if (radioGroup.checkedRadioButtonId == R.id.maleRadioButton) {
            return true
        }
        return false
    }
}



