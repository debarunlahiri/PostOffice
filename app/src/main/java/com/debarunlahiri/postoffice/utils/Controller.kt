package com.debarunlahiri.postoffice.utils

import java.util.regex.Pattern

class Controller {

    companion object {
        fun isValidPincode(pincode: String): Boolean {
            val pattern = Pattern.compile("^[1-9][0-9]{5}\$")
            val matcher = pattern.matcher(pincode)
            return matcher.matches()
        }
    }

}