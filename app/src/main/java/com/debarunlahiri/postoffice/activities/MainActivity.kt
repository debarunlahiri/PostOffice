package com.debarunlahiri.postoffice.activities

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.debarunlahiri.postoffice.R
import com.debarunlahiri.postoffice.adapters.PincodeAdapter
import com.debarunlahiri.postoffice.models.Pincode
import com.debarunlahiri.postoffice.models.PincodeResponse
import com.debarunlahiri.postoffice.utils.Constants
import com.google.gson.Gson
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar

import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AlertDialog
import com.debarunlahiri.postoffice.utils.Controller


class MainActivity : AppCompatActivity(), PincodeAdapter.OnItemClickListener {

    private val TAG: String = MainActivity::class.java.name
    private lateinit var mContext: Context
    private lateinit var etPincode: EditText
    private lateinit var pbMain: ProgressBar
    private lateinit var rvPincode: RecyclerView

    private var pincodeList: MutableList<Pincode> = mutableListOf()
    private lateinit var pincodeAdapter: PincodeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this

        etPincode = findViewById(R.id.etPincode)
        rvPincode = findViewById(R.id.rvPincode)
        pbMain = findViewById(R.id.pbMain)

        pbMain.visibility = View.GONE

        pincodeAdapter = PincodeAdapter(mContext, pincodeList, this)
        rvPincode.adapter = pincodeAdapter
        rvPincode.layoutManager = LinearLayoutManager(mContext)


        etPincode.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val pincode = etPincode.text.toString()
                if (pincode.isEmpty()) {
                    etPincode.error = "Please enter pincode"
                } else if (!Controller.isValidPincode(pincode)) {
                    etPincode.error = "Invalid Pincode"
                } else {
                    getPincode(pincode)
                }
                return@OnEditorActionListener true
            }
            false
        })

    }

    private fun getPincode(pincode: String) {
        pincodeList.clear()
        pincodeAdapter.notifyDataSetChanged()
        pbMain.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(mContext)
        val stringRequest = StringRequest(Request.Method.GET, Constants.Urls.PINCODE + pincode, {
            if (it != null) {
                pbMain.visibility = View.GONE
                val gson = Gson()
                val pincode: MutableList<PincodeResponse> =
                    gson.fromJson(it, Array<PincodeResponse>::class.java).toList() as MutableList<PincodeResponse>
                if (pincode[0].Status == Constants.Keys.STATUS_SUCCESS) {
                    Log.e(TAG, "getPincode: ${pincode[0]}")
                    pincodeList.addAll(pincode[0].PostOffice)
                    pincodeAdapter.setPincodeList(pincodeList)
                } else {
                    Toast.makeText(mContext, pincode[0].Message, Toast.LENGTH_LONG).show()
                }
            }
        }, {
            pbMain.visibility = View.GONE
            Log.e(TAG, "getPincode: ${it.localizedMessage}")
        })
        queue.add(stringRequest)
    }

    override fun onItemClick(pincode: Pincode) {
        Log.d(TAG, "onItemClick: $pincode")
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle(pincode.BranchType)
        builder.setMessage("Name: ${pincode.Name} \n" +
                "BranchType: ${pincode.BranchType} \n" +
                "DeliveryStatus: ${pincode.DeliveryStatus} \n" +
                "Circle: ${pincode.Circle} \n" +
                "District: ${pincode.District} \n" +
                "Division: ${pincode.Division} \n" +
                "Region: ${pincode.Region} \n" +
                "Block: ${pincode.Block} \n" +
                "State: ${pincode.State} \n" +
                "Country: ${pincode.Country} \n" +
                "Pincode: ${pincode.Pincode} \n")
        builder.setPositiveButton("Okay", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                p0?.dismiss()
            }
        })
        builder.show()
    }
}