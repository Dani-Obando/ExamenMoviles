package com.example.casino

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var birthdateTextView: TextView
    private lateinit var playerNameEditText: EditText
    private lateinit var betAmountEditText: EditText
    private lateinit var diceCountSpinner: Spinner
    private lateinit var enterButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        birthdateTextView = findViewById(R.id.birthdate)
        playerNameEditText = findViewById(R.id.playerName)
        betAmountEditText = findViewById(R.id.betAmount)
        diceCountSpinner = findViewById(R.id.diceCount)
        enterButton = findViewById(R.id.enterButton)



        birthdateTextView.setOnClickListener {
            showDatePicker()
        }

        enterButton.setOnClickListener {
            validateInputs()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            birthdateTextView.text = selectedDate
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun validateInputs() {
        val playerName = playerNameEditText.text.toString().trim()
        val birthdate = birthdateTextView.text.toString().trim()
        val betAmountString = betAmountEditText.text.toString().trim()

        if (playerName.isEmpty()) {
            showToast("Por favor, ingresa tu nombre.")
            return
        }

        if (birthdate.isEmpty()) {
            showToast("Por favor, selecciona tu fecha de nacimiento.")
            return
        }

        val betAmount = betAmountString.toDoubleOrNull()
        if (betAmount == null || betAmount < 2000000) {
            showToast("El monto de apuesta debe ser mayor o igual a 2 millones.")
            return
        }

        val birthYear = birthdate.split("/")[2].toInt()
        val age = Calendar.getInstance().get(Calendar.YEAR) - birthYear
        if (age < 21) {
            showToast("No se le permite ingresar. Debe ser mayor o igual a 21 años.")
            return
        }

        val diceCount = diceCountSpinner.selectedItem.toString().toInt()
        if (diceCount < 2 || diceCount > 3) {
            showToast("La cantidad de dados debe ser 2 o 3.")
            return
        }

        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("PLAYER_NAME", playerName)
        intent.putExtra("AVAILABLE_AMOUNT", "2000000")
        intent.putExtra("BET_AMOUNT", betAmountString)
        intent.putExtra("DICE_COUNT", diceCount)
        startActivity(intent)
    }




    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
