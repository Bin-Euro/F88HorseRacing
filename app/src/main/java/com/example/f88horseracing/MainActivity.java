package com.example.f88horseracing;

import static com.example.f88horseracing.R.*;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView balanceAmountTextView;
    private CheckBox checkBox1, checkBox2, checkBox3;
    private EditText editText1, editText2, editText3;
    private SeekBar seekBar1, seekBar2, seekBar3;
    private Button startButton, resetButton;
    boolean isIncreasing1 = true;
    boolean isIncreasing2 = true;
    boolean isIncreasing3 = true;
    int bet1 = 0;
    int bet2 = 0;
    int bet3 = 0;
    private ListView betListView;
    private int balance = 10000;
    private List<BetItem> betList = new ArrayList<>();
    private BetAdapter betAdapter;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        balanceAmountTextView = findViewById(R.id.balanceAmountTextView);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        seekBar1 = findViewById(R.id.seekBar1);
        seekBar2 = findViewById(R.id.seekBar2);
        seekBar3 = findViewById(R.id.seekBar3);
        startButton = findViewById(R.id.startButton);
        resetButton = findViewById(R.id.resetButton);
        betListView = findViewById(R.id.betListView);

        betAdapter = new BetAdapter(this, betList);
        betListView.setAdapter(betAdapter);

        balanceAmountTextView.setText(balance + "$");

        checkBox1.setOnCheckedChangeListener((buttonView, isChecked) -> editText1.setEnabled(isChecked));
        checkBox2.setOnCheckedChangeListener((buttonView, isChecked) -> editText2.setEnabled(isChecked));
        checkBox3.setOnCheckedChangeListener((buttonView, isChecked) -> editText3.setEnabled(isChecked));

        startButton.setOnClickListener(v -> startRace());
        resetButton.setOnClickListener(v -> resetRace());
        resetRace();
    }


    private void startRace() {
        resetButton.setEnabled(false);
        startButton.setEnabled(false);
        bet1 = getBetAmount(editText1);
        bet2 = getBetAmount(editText2);
        bet3 = getBetAmount(editText3);
        isIncreasing1 = true;
        isIncreasing2 = true;
        isIncreasing3 = true;
        checkBox1.setEnabled(false);
        checkBox2.setEnabled(false);
        checkBox3.setEnabled(false);
        editText1.setEnabled(false);
        editText2.setEnabled(false);
        editText3.setEnabled(false);

        int totalBet = bet1 + bet2 + bet3;
        if (totalBet == 0) {
            Toast.makeText(this, "Please place at least one bet to start the race!", Toast.LENGTH_SHORT).show();
            startButton.setEnabled(true);
            return;
        }

        if (totalBet > balance) {
            Toast.makeText(this, "Total bet exceeds balance!", Toast.LENGTH_SHORT).show();
            startButton.setEnabled(true);
            return;
        }

        balanceAmountTextView.setText(balance + "$");

        Handler handler = new Handler();
        Random random = new Random();

        final int ProgressStep = 4;

        // Biến boolean để theo dõi hướng đi của SeekBar


        Runnable race = new Runnable() {
            @Override
            public void run() {
                boolean raceOver = false;

                // Tăng dần SeekBar1 lên giá trị max
                if (isIncreasing1) {
                    if (seekBar1.getProgress() < seekBar1.getMax()) {
                        int nextProgress1 = seekBar1.getProgress() + random.nextInt(ProgressStep);
                        seekBar1.setProgress(Math.min(nextProgress1, seekBar1.getMax()));
                    } else {
                        // Khi đạt giá trị max, chuyển hướng sang giảm dần
                        isIncreasing1 = false;
                        seekBar1.setThumb(getResources().getDrawable(R.drawable.hourse1));
                    }
                } else {
                    // Giảm dần SeekBar1 về giá trị min
                    if (seekBar1.getProgress() > 0) {
                        int nextProgress1 = seekBar1.getProgress() - random.nextInt(ProgressStep);
                        seekBar1.setProgress(Math.max(nextProgress1, 0));
                    } else {
                        // Khi đạt giá trị min, kết thúc cuộc đua
                        raceOver = true;
                        showWinner(1);
                    }
                }

                if (isIncreasing2) {
                    if (seekBar2.getProgress() < seekBar2.getMax()) {
                        int nextProgress2 = seekBar2.getProgress() + random.nextInt(ProgressStep);
                        seekBar2.setProgress(Math.min(nextProgress2, seekBar2.getMax()));
                    } else {
                        isIncreasing2 = false;
                        seekBar2.setThumb(getResources().getDrawable(R.drawable.hourse1));
                    }
                } else {
                    if (seekBar2.getProgress() > 0) {
                        int nextProgress2 = seekBar2.getProgress() - random.nextInt(ProgressStep);
                        seekBar2.setProgress(Math.max(nextProgress2, 0));
                    } else {
                        raceOver = true;
                        showWinner(2);
                    }
                }

                // Tương tự cho SeekBar3
                if (isIncreasing3) {
                    if (seekBar3.getProgress() < seekBar3.getMax()) {
                        int nextProgress3 = seekBar3.getProgress() + random.nextInt(ProgressStep);
                        seekBar3.setProgress(Math.min(nextProgress3, seekBar3.getMax()));
                    } else {
                        isIncreasing3 = false;
                        seekBar3.setThumb(getResources().getDrawable(R.drawable.hourse1));
                    }
                } else {
                    if (seekBar3.getProgress() > 0) {
                        int nextProgress3 = seekBar3.getProgress() - random.nextInt(ProgressStep);
                        seekBar3.setProgress(Math.max(nextProgress3, 0));
                    } else {
                        raceOver = true;
                        showWinner(3);
                    }
                }
                if (!raceOver) {
                    handler.postDelayed(this, 50);
                }
            }
        };

        handler.post(race);
    }


    private void resetRace() {
        mediaPlayer = MediaPlayer.create(this, R.raw.xoso);
        mediaPlayer.start();
        startButton.setEnabled(true);
        checkBox1.setChecked(false);
        checkBox2.setChecked(false);
        checkBox3.setChecked(false);
        editText1.setText("");
        editText2.setText("");
        editText3.setText("");
        seekBar1.setProgress(0);
        seekBar2.setProgress(0);
        seekBar3.setProgress(0);
        seekBar1.setThumb(getResources().getDrawable(R.drawable.horse2));
        seekBar2.setThumb(getResources().getDrawable(R.drawable.horse2));
        seekBar3.setThumb(getResources().getDrawable(R.drawable.horse2));
        checkBox1.setEnabled(true);
        checkBox2.setEnabled(true);
        checkBox3.setEnabled(true);
    }

    private int getBetAmount(@NonNull EditText editText) {
        String betString = editText.getText().toString();
        if (betString.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(betString);
    }

    private void showWinner(int winningHorseNumber) {
        resetButton.setEnabled(true);
        List<BetItem> newBets = new ArrayList<>();
        int totalWinningAmount = 0;

        if (checkBox1.isChecked()) {
            int betAmount1 = bet1;
            int plusOrMinus1 = (winningHorseNumber == 1) ? betAmount1 * 1 : -betAmount1;
            balance += plusOrMinus1;
            totalWinningAmount += plusOrMinus1;
            String dateTime1 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            BetItem betItem1 = new BetItem(dateTime1, 1, winningHorseNumber, plusOrMinus1);
            newBets.add(betItem1);
        }

        if (checkBox2.isChecked()) {
            int betAmount2 = bet2;
            int plusOrMinus2 = (winningHorseNumber == 2) ? betAmount2 * 1 : -betAmount2;
            balance += plusOrMinus2;
            totalWinningAmount += plusOrMinus2;
            String dateTime2 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            BetItem betItem2 = new BetItem(dateTime2, 2, winningHorseNumber, plusOrMinus2);
            newBets.add(betItem2);
        }

        if (checkBox3.isChecked()) {
            int betAmount3 = bet3;
            int plusOrMinus3 = (winningHorseNumber == 3) ? betAmount3 * 1 : -betAmount3;
            balance += plusOrMinus3;
            totalWinningAmount += plusOrMinus3;
            String dateTime3 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            BetItem betItem3 = new BetItem(dateTime3, 3, winningHorseNumber, plusOrMinus3);
            newBets.add(betItem3);
        }

        balanceAmountTextView.setText(balance + "$");
        betList.addAll(newBets);
        betAdapter.notifyDataSetChanged();
        Log.d("HorseRacing", "Total Winning Amount: " + totalWinningAmount);
        if (!newBets.isEmpty()) {
            String title = totalWinningAmount > 0 ? "Winner" : "Loser";
            BetItem firstBet = newBets.get(0); // Assume all bets have the same dateTime and winningHorseNumber
            //showWinnerPopup(title, firstBet.getDateTime(), firstBet.getBetHorseNumber(), winningHorseNumber, totalWinningAmount);
            displayWiningPopupDialog(winningHorseNumber, newBets);
        }

    }
//    private void showWinnerPopup(String title, String dateTime, int betHorseNumber, int winningHorseNumber) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(title)
//                .setMessage("Date: " + dateTime + "\n" +
//                        "Bet Horse Number: " + betHorseNumber + "\n" +
//                        "Winning Horse Number: " + winningHorseNumber)
//                .setPositiveButton("OK", null); // Nút "OK" để đóng popup
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
    private void showWinnerPopup(String title, String dateTime, int betHorseNumber, int winningHorseNumber,int totalWinningAmount) {
        Intent intent = new Intent(MainActivity.this, WinnerActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("dateTime", dateTime);
        intent.putExtra("betHorseNumber", betHorseNumber);
        intent.putExtra("winningHorseNumber", winningHorseNumber);
        intent.putExtra("totalWinningAmount", totalWinningAmount);
        startActivity(intent);
    }

    private void displayWiningPopupDialog(int winingHorseNumber, List<BetItem> listbetItem) {
        Dialog popupDialog = new Dialog(this);
        popupDialog.setCancelable(false);
        popupDialog.setContentView(R.layout.popup_dialog);
        Objects.requireNonNull(popupDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView txtWiningHorseNumber = (TextView) popupDialog.findViewById(R.id.wining_horse);
        TextView txtBetHorseNumber = (TextView) popupDialog.findViewById(R.id.choose_horse);
        Button btnBack = (Button) popupDialog.findViewById(R.id.btnBack);
        TextView txtMoney = (TextView) popupDialog.findViewById(R.id.money);
        SeekBar skbHorse1 = (SeekBar) findViewById(R.id.seekBar1);
        SeekBar skbHorse2 = (SeekBar) findViewById(R.id.seekBar2);
        SeekBar skbHorse3 = (SeekBar) findViewById(R.id.seekBar3);
        int amountMoney = 0;
        String betHorses = "";
        int flags = 1;
        for (BetItem betItem: listbetItem) {
            amountMoney += betItem.getPlusOrMinus();
            if (flags > 1){
                betHorses += ", ";
            }
            betHorses += "Number " + betItem.getBetHorseNumber();
            flags++;
        }
        mediaPlayer.stop();
        if (amountMoney > 0) {
            mediaPlayer = MediaPlayer.create(this, R.raw.win);
            mediaPlayer.start();
            txtMoney.setTextColor(Color.parseColor("#0AFF00"));
            txtMoney.setText("+" + amountMoney);
        }else{
            mediaPlayer = MediaPlayer.create(this, R.raw.lose);
            mediaPlayer.start();
            txtMoney.setTextColor(Color.parseColor("#FF2F2F"));
            txtMoney.setText("" + amountMoney);
        }

        switch (winingHorseNumber) {
            case 1: {
                ColorStateList thumbTintList = skbHorse1.getThumbTintList();
                if (thumbTintList != null) {
                    int thumbTintColor = thumbTintList.getDefaultColor();
                    txtWiningHorseNumber.setTextColor(thumbTintColor);
                }
                break;
            }
            case 2: {
                ColorStateList thumbTintList = skbHorse2.getThumbTintList();
                if (thumbTintList != null) {
                    int thumbTintColor = thumbTintList.getDefaultColor();
                    txtWiningHorseNumber.setTextColor(thumbTintColor);
                }
                break;
            }
            case 3: {
                ColorStateList thumbTintList = skbHorse3.getThumbTintList();
                if (thumbTintList != null) {
                    int thumbTintColor = thumbTintList.getDefaultColor();
                    txtWiningHorseNumber.setTextColor(thumbTintColor);
                }
                break;
            }
            default:
                txtWiningHorseNumber.setTextColor(Color.BLACK);
                break;
        }
        txtWiningHorseNumber.setText("Number " + winingHorseNumber);
        txtBetHorseNumber.setText(betHorses);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupDialog.cancel();
                mediaPlayer.stop();
            }
        });
        popupDialog.show();
    }
}
