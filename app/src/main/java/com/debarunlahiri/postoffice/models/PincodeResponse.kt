package com.debarunlahiri.postoffice.models

data class PincodeResponse(var Message: String, var Status: String, var PostOffice: ArrayList<Pincode>) {

    var default_value = ""
    get() = field
    set(value) {field = value}
}