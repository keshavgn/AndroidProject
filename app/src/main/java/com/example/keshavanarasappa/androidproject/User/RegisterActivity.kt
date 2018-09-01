package com.example.keshavanarasappa.androidproject.user

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import com.example.keshavanarasappa.androidproject.R
import com.example.keshavanarasappa.androidproject.common.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class RegisterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val calender = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calender.set(Calendar.YEAR, year)
            calender.set(Calendar.MONTH, monthOfYear)
            calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.US)
            dateEditText.text = dateFormat.format(calender.time)
        }

        dateEditText.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                    calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH),
                    calender.get(Calendar.DAY_OF_MONTH)).show()
        }

        registerButton.setOnClickListener {
            val realm = RealmManager.instance.getRealm()
            var errorMessage: String? = null
            if (!emailValidator(emailIdEditText.text.toString())) {
                errorMessage = EMAIL_INVALID
            } else if (passwordEditText.text.length < 4) {
                errorMessage = PASSWORD_SHORT
            } else if (realm?.where(User::class.java)?.equalTo(EMAIL_ID, emailIdEditText.text.toString())?.findFirst() != null) {
                errorMessage = EMAIL_EXISTS
            }

            if (errorMessage != null) {
                Toast.makeText(this, ERROR.plus(errorMessage), Toast.LENGTH_LONG).show()
            } else {
                realm?.executeTransaction {
                    val user = realm.createObject(User::class.java, emailIdEditText.text.toString())
                    user.password = passwordEditText.text.toString()
                    user.dob = dateEditText.text.toString()
                    user.sex = if (maleSelected() == true) MALE else FEMALE
                    user.status = statusButton.isChecked
//                    saveCredentials(username = user.emailId, password = user.password)
                    finish()
                }
            }
        }
    }

    private fun emailValidator(email: String): Boolean {
        val emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val pattern = Pattern.compile(emailPattern)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    private fun maleSelected(): Boolean {
        return radioGroup.checkedRadioButtonId == R.id.maleRadioButton
    }


    companion object {
        private const val DATE_FORMAT = "dd.MM.yyyy"
        private const val EMAIL_ID = "emailId"
        private const val MALE = "Male"
        private const val FEMALE = "Female"
        private const val PASSWORD_SHORT = "Password is too short"
        private const val EMAIL_EXISTS = "Email address already exists!, Choose different"
        private const val EMAIL_INVALID= "Email address is invalid"
        private const val ERROR = "Error: "
    }

}



