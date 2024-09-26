package com.diegobpaula.goodluck

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.edit_numbers)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        // database prefers
        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val resultRecovery = prefs.getString("result", "no record saved")

        resultRecovery?.let { txtResult.text = "bet recovered: $resultRecovery" }

        txtResult.text = "$resultRecovery"

        btnGenerate.setOnClickListener {
            val number = editText.text.toString()
            numberGenerate(number, txtResult)
        }
    }

    private fun numberGenerate(text: String, txtResult: TextView) {

        if (text.isEmpty()) {
            Toast.makeText(this, "Enter a number between 6 and 15", Toast.LENGTH_SHORT).show()
            return
        }

        val qtd = text.toInt()

        if (qtd < 6 || qtd > 15) {
            Toast.makeText(this, "Enter a number between 6 and 15", Toast.LENGTH_SHORT).show()
            return
        }

        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)

            if (numbers.size == qtd) {
                break
            }
        }

        val numberSorted = numbers.sorted()
        txtResult.text = numberSorted.joinToString("-")

        // record sharedPreferences
        prefs.edit().apply() {
            // commit -> sincrona : dados simples  | apply -> assicrona : dados robustos (interface)
            putString("result", txtResult.text.toString())
            apply()
        }
    }
}