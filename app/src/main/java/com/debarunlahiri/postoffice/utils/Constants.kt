package com.debarunlahiri.postoffice.utils

interface Constants {

    class Urls {
        companion object {
            const val BASE_URL = "https://api.postalpincode.in/"
            const val PINCODE = BASE_URL + "pincode/"
        }
    }

    class Keys {
        companion object {
            const val STATUS_SUCCESS = "Success"
            const val STATUS_404 = "404"
        }
    }
}