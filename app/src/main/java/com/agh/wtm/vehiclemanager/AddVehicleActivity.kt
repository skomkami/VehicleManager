package com.agh.wtm.vehiclemanager

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.agh.wtm.vehiclemanager.db.VehicleContract
import com.agh.wtm.vehiclemanager.db.VehicleDBHelper
import com.agh.wtm.vehiclemanager.fragments.VehicleManagerFragment
import com.agh.wtm.vehiclemanager.model.Vehicle

class AddVehicleActivity : AppCompatActivity() {

    private var addVehicleBtn: Button? = null
    private var vehicleNameInput: EditText? = null
    private var vehicleTypeInput: Spinner? = null
    private var vehicleMileageInput: EditText? = null
    private var dbHelper: VehicleDBHelper? = null
    private var returnBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_vehicle)
        addVehicleBtn = findViewById(R.id.add_vehicle_btn)
        vehicleNameInput = findViewById(R.id.vehicle_name_field)
        vehicleTypeInput = findViewById(R.id.vehicle_type_input)
        vehicleMileageInput = findViewById(R.id.mileage_input_field)
        returnBtn = findViewById(R.id.btnBackVehicle)
        vehicleTypeInput!!.adapter = ArrayAdapter(this, android.R.layout.simple_selectable_list_item, Vehicle.VehicleType.values())
        dbHelper = VehicleDBHelper(this, VehicleContract.tables)

        if (intent.getBooleanExtra("emptyVehicleList", false)){
            returnBtn!!.isEnabled = false
            returnBtn!!.visibility = View.GONE
            onBackPressed()
        }

        addVehicleBtn!!.setOnClickListener {
            run {
                if(vehicleNameInput!!.text.toString().isEmpty() ||
                    vehicleMileageInput!!.text.toString().toIntOrNull() == null) {
                    Toast.makeText(this, "Please fill all fields correctly", Toast.LENGTH_LONG).show()
                    return@run
                }else if (vehicleNameInput!!.text.toString().length >20){
                    Toast.makeText(this, "Name is to long! \n Max 20 signs.", Toast.LENGTH_LONG).show()
                    return@run
                }

                val vehicleName = vehicleNameInput!!.text.toString()
                val vehicleType = vehicleTypeInput!!.selectedItem.toString()
                val mileage = vehicleMileageInput!!.text.toString().toInt()

                val newVehicle = Vehicle(0, vehicleName, Vehicle.VehicleType.valueOf(vehicleType), mileage)
                dbHelper!!.insert(VehicleContract.VehicleEntry, newVehicle)

                vehicleNameInput!!.text.clear()
                vehicleMileageInput!!.text.clear()

                val intent = Intent("com.agh.wtm.vehiclemanager.UPDATE_SPINNER")
                sendBroadcast(intent)
                finish()
            }
        }
        returnBtn!!.setOnClickListener{
            finish()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }

}