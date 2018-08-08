package com.example.keshavanarasappa.androidproject.room

import android.app.DialogFragment
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keshavanarasappa.androidproject.R
import kotlinx.android.synthetic.main.fragment_new_contact.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [NewContactFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [NewContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class NewContactFragment : DialogFragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dialog.window.attributes.windowAnimations = R.style.DialogAnimation
        errorTextView.visibility = View.INVISIBLE

        saveButton.setOnClickListener {
            var error: String = " "
            if (nameEditText.text.length < 4) {
                error = "Name should be minimum 4 characters"
            } else if (phoneNoEditText.text.length != 10) {
                error = "Phone number should be 10 characters"
            } else if (cityEditText.text.length < 3) {
                error = "City should be minimum 3 characters"
            }
            if (error.length > 1) {
                errorTextView.text = error
                errorTextView.visibility = View.VISIBLE
            } else {
                saveContact()
                dismiss()
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_contact, container, false)
    }

    private fun saveContact() {
        listener?.onFragmentInteraction(name = nameEditText.text.toString(), phoneNumber = phoneNoEditText.text.toString().toLong(), city = cityEditText.text.toString())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(name: String, phoneNumber: Long, city: String)
    }

    companion object {
        fun newInstance() = NewContactFragment()
    }
}
