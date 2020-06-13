package com.agh.wtm.vehiclemanager

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.agh.wtm.vehiclemanager.db.VehicleContract
import com.agh.wtm.vehiclemanager.db.VehicleDBHelper
import com.agh.wtm.vehiclemanager.model.Vehicle

import kotlinx.android.synthetic.main.activity_add_vehicle.*

class AddVehicleActivity : AppCompatActivity() {

    private var addVehicleBtn: Button? = null
    private var vehicleNameInput: EditText? = null
    private var vehicleTypeInput: Spinner? = null
    private var vehicleMileageInput: EditText? = null
    private var dbHelper: VehicleDBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_vehicle)
        addVehicleBtn = findViewById(R.id.add_vehicle_btn)
        vehicleNameInput = findViewById(R.id.vehicle_name_field)
        vehicleTypeInput = findViewById(R.id.vehicle_type_input)
        vehicleMileageInput = findViewById(R.id.mileage_input_field)
        vehicleTypeInput!!.adapter = ArrayAdapter(this, android.R.layout.simple_selectable_list_item, Vehicle.VehicleType.values())
        dbHelper = VehicleDBHelper(this, VehicleContract.tables)

        addVehicleBtn!!.setOnClickListener {
            run {
                val vehicleName = vehicleNameInput!!.text.toString()
                val vehicleType = vehicleTypeInput!!.selectedItem.toString()
                val mileage = vehicleMileageInput!!.text.toString().toInt()

                val newVehicle = Vehicle(0, vehicleName, Vehicle.VehicleType.valueOf(vehicleType), mileage)
                dbHelper!!.insert(VehicleContract.VehicleEntry, newVehicle)

                vehicleNameInput!!.text.clear()
                vehicleMileageInput!!.text.clear()
                TODO("Broadcast")
                /*val mainActivity: MainActivity =  fragment as MainActivity
                mainActivity.addToSpinner(vehicleName)*/
            }
        }
    }

}