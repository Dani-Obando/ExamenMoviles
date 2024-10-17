package com.example.casino

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var playerNameTextView: TextView
    private lateinit var availableAmountTextView: TextView
    private lateinit var betAmountTextView: TextView
    private lateinit var diceContainer: LinearLayout
    private lateinit var launchDiceButton: Button
    private lateinit var statusImageView: ImageView
    private lateinit var radioButtons: List<RadioButton>
    private lateinit var betAmountEditText: EditText

    private var totalAmount: Int = 0
    private var betAmount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        playerNameTextView = findViewById(R.id.playerNameTextView)
        availableAmountTextView = findViewById(R.id.availableAmountTextView)
        betAmountTextView = findViewById(R.id.betAmountTextView)
        diceContainer = findViewById(R.id.diceContainer)
        launchDiceButton = findViewById(R.id.launchDiceButton)
        statusImageView = findViewById(R.id.statusImageView)
        betAmountEditText = findViewById(R.id.betAmountEditText)

        val playerName = intent.getStringExtra("PLAYER_NAME") ?: "Jugador"
        totalAmount = intent.getStringExtra("AVAILABLE_AMOUNT")?.toInt() ?: 0
        val diceCount = intent.getIntExtra("DICE_COUNT", 3)

        playerNameTextView.text = "Jugador: $playerName"
        availableAmountTextView.text = "Monto Disponible: $totalAmount"

        radioButtons = listOf(
            findViewById(R.id.bet01),
            findViewById(R.id.bet02),
            findViewById(R.id.bet03),
            findViewById(R.id.bet04),
            findViewById(R.id.bet05),
            findViewById(R.id.bet06),
            findViewById(R.id.bet07),
            findViewById(R.id.bet08),
            findViewById(R.id.bet09),
            findViewById(R.id.bet10),
            findViewById(R.id.bet11),
            findViewById(R.id.bet12),
            findViewById(R.id.bet13),
            findViewById(R.id.bet14),
            findViewById(R.id.bet15),
            findViewById(R.id.bet16),
            findViewById(R.id.bet17),
            findViewById(R.id.bet18)
        )

        showSelectedDice(diceCount)
        setupBetOptions(diceCount)

        launchDiceButton.setOnClickListener {
            val betInput = betAmountEditText.text.toString()
            if (betInput.isNotEmpty()) {
                betAmount = betInput.toInt()
                if (betAmount <= totalAmount && betAmount > 0) {
                    if (validateBetSelection()) {
                        randomizeDice()
                    } else {
                        Toast.makeText(this, "Por favor, selecciona una apuesta.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "El monto apostado no puede ser mayor al monto disponible ni menor o igual a cero.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, ingresa un monto apostado.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showSelectedDice(diceCount: Int) {
        diceContainer.removeAllViews()
        for (i in 1..diceCount) {
            val diceImageView = ImageView(this)
            val diceResId = resources.getIdentifier("dice1", "drawable", packageName)
            if (diceResId != 0) {
                diceImageView.setImageResource(diceResId)
                diceImageView.layoutParams = LinearLayout.LayoutParams(100, 100)
                diceImageView.setPadding(8, 0, 8, 0)
                diceContainer.addView(diceImageView)
            }
        }
    }

    private fun randomizeDice() {
        var total = 0
        for (i in 0 until diceContainer.childCount) {
            val diceImageView = diceContainer.getChildAt(i) as ImageView
            val randomNumber = Random.nextInt(1, 7)
            total += randomNumber
            val diceResId = resources.getIdentifier("dice$randomNumber", "drawable", packageName)
            if (diceResId != 0) {
                diceImageView.setImageResource(diceResId)
            }
        }
        checkBetResult(total)
        for (button in radioButtons) {
            button.isChecked = false
        }
    }

    private fun checkBetResult(total: Int) {
        val selectedBetId = radioButtons.find { it.isChecked }?.id ?: -1
        if (selectedBetId != -1) {
            val selectedBetValue = findViewById<RadioButton>(selectedBetId).text.toString().toInt()
            if (total == selectedBetValue) {
                totalAmount += (betAmount*2)
                statusImageView.setImageResource(R.drawable.win)
                Toast.makeText(this, "¡Ganaste!", Toast.LENGTH_SHORT).show()
            } else {
                totalAmount -= betAmount
                if (totalAmount < 0) totalAmount = 0
                statusImageView.setImageResource(R.drawable.loose)
                Toast.makeText(this, "Perdiste.", Toast.LENGTH_SHORT).show()
            }
            availableAmountTextView.text = "Monto Disponible: $totalAmount"
        } else {
            Toast.makeText(this, "Selecciona una apuesta válida.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateBetSelection(): Boolean {
        return radioButtons.any { it.isChecked }
    }

    private fun setupBetOptions(diceCount: Int) {
        for (button in radioButtons) {
            button.visibility = View.GONE
            button.setOnClickListener {
                for (otherButton in radioButtons) {
                    if (otherButton != button) {
                        otherButton.isChecked = false
                    }
                }
            }
        }
        if (diceCount == 2) {
            for (i in 0..10) {
                radioButtons[i].visibility = View.VISIBLE
                radioButtons[i].text = String.format("%02d", i + 2)
            }
        } else if (diceCount == 3) {
            for (i in 0..15) {
                radioButtons[i].visibility = View.VISIBLE
                radioButtons[i].text = String.format("%02d", i + 3)
            }
        }
    }
}
