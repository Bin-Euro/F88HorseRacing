package com.example.f88horseracing;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BetAdapter extends ArrayAdapter<BetItem> {
    public BetAdapter(Context context, List<BetItem> bets) {
        super(context, 0, bets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BetItem betItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bet_item, parent, false);
        }

        TextView textViewDateTime = convertView.findViewById(R.id.textViewDateTime);
        TextView textViewHorseNumberBet = convertView.findViewById(R.id.textViewHorseNumberBet);
        TextView textViewHorseNumberWin = convertView.findViewById(R.id.textViewHorseNumberWin);
        TextView textViewPlusOrMinus = convertView.findViewById(R.id.plusOrMinusTextView);

        textViewDateTime.setText(betItem.getDateTime());
        textViewHorseNumberBet.setText(String.valueOf(betItem.getBetHorseNumber()));
        textViewHorseNumberWin.setText(String.valueOf(betItem.getWinningHorseNumber()));
        textViewPlusOrMinus.setText(String.valueOf(betItem.getPlusOrMinus()));
        TextView plusOrMinusTextView = convertView.findViewById(R.id.plusOrMinusTextView);

        String plusOrMinusString = betItem.getFormattedPlusOrMinus();
        SpannableString spannableString = new SpannableString(plusOrMinusString);
        int color = (betItem.getPlusOrMinus() > 0) ? Color.GREEN : Color.RED;
        spannableString.setSpan(new ForegroundColorSpan(color), 0, plusOrMinusString.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        plusOrMinusTextView.setText(spannableString);
        return convertView;
    }
}

