package com.miko.falco

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.*
import kotlinx.android.synthetic.main.activity_add_produce.*
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter
import com.google.firebase.firestore.FirebaseFirestore


class AddProduceActivity : AppCompatActivity() {

    private val tag = "AddProduceActivity"

    private var numItems : String = ""
    private var unitType : String = ""
    val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_produce)

        var res: Resources = resources

        setUpNumProduceSpinnerListener(res)

        setUpProduceUnitSpinnerListener(res)

        getProduceList()

        addProduce_button.setOnClickListener{
            storeData()
        }
    }

    private fun setUpNumProduceSpinnerListener(res : Resources) {
        // Get spinner instance
        var numProduceSpinner = findViewById<Spinner>(R.id.numProduce_Spinner)

        // Bind to adapter view's onItemSelectedListener
        numProduceSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(tag, "numProduce is not clicked! Defaulting to (1)")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val numArray = res.getStringArray(R.array.num_list)
                Log.d(tag, "Num of items = " + numArray[position])
                numItems = numArray[position]
            }
        }
    }

    private fun setUpProduceUnitSpinnerListener(res : Resources) {
        var produceUnit = findViewById<Spinner>(R.id.produceUnit_Spinner)

        produceUnit.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(tag, "numProduce is not clicked! Defaulting to (1)")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val unitArray = res.getStringArray(R.array.unit_list)
                Log.d(tag, "Produce unit = " + unitArray[position])
                unitType = unitArray[position]
            }

        }
    }

    private fun getProduceList() {
        db.collection("produce-list")
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    Log.d(tag, "${document.id} => ${document.data}")
                    @Suppress("UNCHECKED_CAST")
                    var items = document.data["fruits"] as List<String>
                    @Suppress("UNCHECKED_CAST")
                    items += document.data["vegetables"] as List<String>
                    if (items == emptyList<String>())
                        Log.d(tag, "Produce List is Empty! Check database to see if entries are there!")
                    setUpProduceNameAutoComplete(items)
                }
            }
            .addOnFailureListener{
                Log.d(tag, "Error getting documents.", it)
            }
    }

    private fun setUpProduceNameAutoComplete(produceList : List<String>) {
        Log.d(tag, "$produceList")

        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line, produceList
        )
        val textView = findViewById<AutoCompleteTextView>(R.id.produceName_autoCompleteTextView)
        textView.setAdapter<ArrayAdapter<String>>(adapter)
    }

    private fun storeData() {
        Log.d(tag, "storeData button clicked.")
        val produceName = produceName_autoCompleteTextView.text.toString()

        Log.d(tag, "produce name: $produceName num items: $numItems, item unit: $unitType")
    }


}
