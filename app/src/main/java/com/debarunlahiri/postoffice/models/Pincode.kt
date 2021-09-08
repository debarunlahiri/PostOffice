package com.debarunlahiri.postoffice.models

data class Pincode(var Name: String, var Description: String,var BranchType: String,var DeliveryStatus: String,var Circle: String,
                   var District: String,var Division: String,var Region: String,var Block: String,
                   var State: String,var Country: String,var Pincode: String) {

//    constructor(): this("", "", "", "", "", "", "", "",
//        "", "", "", "", "", "")

    var default_value = ""
    get() = field
    set(value) {field = value}
}