package com.example.casino

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        playerNameTextView = findViewById(R.id.playerNameTextView)
        availableAmountTextView = findViewById(R.id.availableAmountTextView)
        betAmountTextView = findViewById(R.id.betAmountTextView)
        diceContainer = findViewById(R.id.diceContainer)
        launchDiceButton = findViewById(R.id.launchDiceButton)
        statusImageView = findViewById(R.id.statusImageView)

        // Obtener datos del Intent
        val playerName = intent.getStringExtra("PLAYER_NAME") ?: "Jugador"
        val availableAmount = intent.getStringExtra("AVAILABLE_AMOUNT") ?: "0"
        val betAmount = intent.getStringExtra("BET_AMOUNT") ?: "0"
        val diceCount = intent.getIntExtra("DICE_COUNT", 3) // Usar 3 por defecto

        // Mostrar datos en TextViews
        playerNameTextView.text = "Jugador: $playerName"
        availableAmountTextView.text = "Monto Disponible: $availableAmount"
        betAmountTextView.text = "Monto Apostado: $betAmount"

        // Inicializar los RadioButtons
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

        // Mostrar dados seleccionados
        showSelectedDice(diceCount)

        // Configurar opciones de apuesta
        setupBetOptions(diceCount)

        // Configurar el botón de lanzar dados
        launchDiceButton.setOnClickListener {
            randomizeDice()
        }
    }

    private fun showSelectedDice(diceCount: Int) {
        diceContainer.removeAllViews()

        // Mostrar el número de dados seleccionados por el usuario
        for (i in 1..diceCount) {
            val diceImageView = ImageView(this)
            val diceResId = resources.getIdentifier("dice1", "drawable", packageName) // Mostrar el dado por defecto

            if (diceResId != 0) {
                diceImageView.setImageResource(diceResId)
                diceImageView.layoutParams = LinearLayout.LayoutParams(100, 100)
                diceImageView.setPadding(8, 0, 8, 0)
                diceContainer.addView(diceImageView)
            }
        }
    }

    private fun randomizeDice() {
        for (i in 0 until diceContainer.childCount) {
            val diceImageView = diceContainer.getChildAt(i) as ImageView
            val randomNumber = Random.nextInt(1, 7) // Número aleatorio del 1 al 6
            val diceResId = resources.getIdentifier("dice$randomNumber", "drawable", packageName)

            if (diceResId != 0) {
                diceImageView.setImageResource(diceResId)
            }
        }
    }

    private fun setupBetOptions(diceCount: Int) {
        for (button in radioButtons) {
            button.visibility = View.GONE
        }

        if (diceCount == 2) {
            for (i in 0..10) { // 2 a 12 (11 opciones)
                radioButtons[i].visibility = View.VISIBLE
                radioButtons[i].text = String.format("%02d", i + 2) // Apuestas de 2 a 12
            }
        } else if (diceCount == 3) {
            for (i in 0..15) { // 3 a 18 (16 opciones)
                radioButtons[i].visibility = View.VISIBLE
                radioButtons[i].text = String.format("%02d", i + 3) // Apuestas de 3 a 18
            }
        }
    }


}
