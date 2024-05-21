package com.example.f88horseracing;

public class BetItem {
    private String dateTime;
    private int betHorseNumber;
    private int winningHorseNumber;
    private int plusOrMinus;

    public BetItem(String dateTime, int betHorseNumber, int winningHorseNumber, int plusOrMinus) {
        this.dateTime = dateTime;
        this.betHorseNumber = betHorseNumber;
        this.winningHorseNumber = winningHorseNumber;
        this.plusOrMinus = plusOrMinus;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getBetHorseNumber() {
        return betHorseNumber;
    }

    public int getWinningHorseNumber() {
        return winningHorseNumber;
    }

    public int getPlusOrMinus() {
        return plusOrMinus;
    }

    public String getFormattedPlusOrMinus() {
        return (plusOrMinus > 0 ? "+" : "") + plusOrMinus;
    }
}


