package com.example.f88horseracing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String dateTime = intent.getStringExtra("dateTime");
            int betHorseNumber = intent.getIntExtra("betHorseNumber", 0);
            int winningHorseNumber = intent.getIntExtra("winningHorseNumber", 0);
            int totalWinningAmount = intent.getIntExtra("totalWinningAmount", 0);

            // Hiển thị thông tin trên giao diện
            TextView titleTextView = findViewById(R.id.titleTextView);
            titleTextView.setText(title);

            TextView dateTimeTextView = findViewById(R.id.dateTimeTextView);
            dateTimeTextView.setText("Date & Time: " + dateTime);

            TextView betHorseNumberTextView = findViewById(R.id.betHorseNumberTextView);
            betHorseNumberTextView.setText("Bet Horse Number: " + betHorseNumber);

            TextView winningHorseNumberTextView = findViewById(R.id.winningHorseNumberTextView);
            winningHorseNumberTextView.setText("Winning Horse Number: " + winningHorseNumber);

            TextView totalWinningAmountTextView = findViewById(R.id.totalWinningAmountTextView);
            totalWinningAmountTextView.setText("Total Winning Amount: " + totalWinningAmount + "$");
        }

        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(v -> finish());
    }
}
