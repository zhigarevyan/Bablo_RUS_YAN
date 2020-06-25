package entity;

import java.util.ArrayList;

public class SMResult {

    String p1Wins;
    String p2Wins;
    String player1;
    String player2;
    ArrayList<Line> lines = new ArrayList<>();
    String winR;
    int p1PeriodWins = 0, p2PeriodWins = 0;

    public int getP1PeriodWins() {
        return p1PeriodWins;
    }

    public int getP2PeriodWins() {
        return p2PeriodWins;
    }

    double p1DayWins = 0, p1DayQuantity = 0;

    ArrayList<String> winStatForEveryTen = new ArrayList<>();
    ArrayList<String> winStatForDay = new ArrayList<>();
    int quantity = 0;
    String prevDate;

    public ArrayList<String> getWinStatForEveryTen() {
        finalizeEveryTenStats();
        return winStatForEveryTen;
    }

    public ArrayList<String> getWinStatForDay() {
        finalizeDayStats();
        System.out.println("\n\n\n--------------SIZE OF DAYS------------------ \n\n\n = "+winStatForDay.size());
        return winStatForDay;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getP1Wins() {
        return p1Wins;
    }

    public void setP1Wins(String p1Wins) {
        this.p1Wins = p1Wins;
    }

    public String getP2Wins() {
        return p2Wins;
    }

    public void setP2Wins(String p2Wins) {
        this.p2Wins = p2Wins;
    }

    public void put(String date, String score, String fora1, String fora2, String total1, String total2, String total, String winR1, String winR2) {
        Line line = new Line(date, score, fora1, fora2, total1, total2, total, winR1, winR2);
        lines.add(line);
        calculateEveryTenWinStat(line.getWinner());
        calculateEveryDayStat(line.getWinner(), line.getDate());
    }

    public void put(String player2, String date, String score, String fora1, String fora2, String total1, String total2, String total) {
        Line line = new Line(player2, date, score, fora1, fora2, total1, total2, total);
        lines.add(line);
        calculateEveryTenWinStat(line.getWinner());
        calculateEveryDayStat(line.getWinner(), line.getDate());
    }

    public void finalizeDayStats() {
        double winRateForDate = p1DayWins / p1DayQuantity * 100.0;
        String delimiterText = String.format("%s | WinR: %.0f%%", prevDate, winRateForDate);
        winStatForDay.add(delimiterText);
        p1DayWins = 0;
        p1DayQuantity = 0;
    }

    public void finalizeEveryTenStats() {
        String delimiterText = String.format("За %d матчей | %d : %d", quantity, p1PeriodWins, p2PeriodWins);
        winStatForEveryTen.add(delimiterText);
    }

    public void calculateEveryTenWinStat(int winner) {
        quantity++;
        if (winner == 1) {
            p1PeriodWins++;
        } else {
            p2PeriodWins++;
        }

        if (quantity % 10 == 0 && quantity != 0) {
            String delimiterText = String.format("За %d матчей | %d : %d", quantity, p1PeriodWins, p2PeriodWins);
            winStatForEveryTen.add(delimiterText);
        }
    }

    public void calculateEveryDayStat(int winner, String date) {
        if (!date.equals(prevDate) && prevDate != null) {
            double winRateForDate = p1DayWins / p1DayQuantity * 100.0;
            String delimiterText = String.format("%s | WinR: %.0f%%", prevDate, winRateForDate);
            winStatForDay.add(delimiterText);
            p1DayWins = 0;
            p1DayQuantity = 0;
        }

        if (winner == 1) {
            p1DayWins++;
        }
        p1DayQuantity++;
        prevDate = date;
    }

    public String getWinR() {
        return winR;
    }

    public void setWinR(String winR) {
        this.winR = winR;
    }

    public Line get(int index) {
        return lines.get(index);
    }

    public int getQuantity() {
        return lines.size();
    }

}
