package com.example.casino

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

    private lateinit var playerNameTextView: TextView
    private lateinit var availableAmountTextView: TextView
    private lateinit var betAmountTextView: TextView
    private lateinit var diceContainer: LinearLayout
    private lateinit var statusImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        playerNameTextView = findViewById(R.id.playerNameTextView)
        availableAmountTextView = findViewById(R.id.availableAmountTextView)
        betAmountTextView = findViewById(R.id.betAmountTextView)
        diceContainer = findViewById(R.id.diceContainer)
        statusImageView = findViewById(R.id.statusImageView)

        // Obtener datos del Intent
        val playerName = intent.getStringExtra("PLAYER_NAME") ?: "Jugador"
        val availableAmount = intent.getStringExtra("AVAILABLE_AMOUNT") ?: "0"
        val betAmount = intent.getStringExtra("BET_AMOUNT") ?: "0"
        val diceCount = intent.getIntExtra("DICE_COUNT", 1)

        // Mostrar datos en TextViews
        playerNameTextView.text = "Jugador: $playerName"
        availableAmountTextView.text = "Monto Disponible: $availableAmount"
        betAmountTextView.text = "Monto Apostado: $betAmount"

        // Mostrar dados seleccionados
        showSelectedDice(diceCount)
    }

    private fun showSelectedDice(diceCount: Int) {
        diceContainer.removeAllViews() // Limpiar el contenedor antes de agregar los dados

        for (i in 1..diceCount) {
            val diceImageView = ImageView(this)
            val diceResId = resources.getIdentifier("dice$i", "drawable", packageName)
            diceImageView.setImageResource(diceResId)
            diceImageView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            diceContainer.addView(diceImageView)
        }
    }
}
