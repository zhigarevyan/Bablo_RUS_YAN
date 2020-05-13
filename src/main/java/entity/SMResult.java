package entity;

import java.util.ArrayList;

public class SMResult {

    String p1Wins;
    String p2Wins;
    String player1;
    String player2;
    ArrayList<Line> lines = new ArrayList<Line>();
    String winR;

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
        Line line = new Line(date,score,fora1,fora2,total1,total2,total,winR1,winR2);
        lines.add(line);
    }

    public void put(String player2, String date, String score, String fora1, String fora2, String total1, String total2, String total) {
        Line line = new Line(player2,date,score,fora1,fora2,total1,total2,total);
        lines.add(line);
    }

    public String getWinR() {
        return winR;
    }

    public void setWinR(String winR) {
        this.winR = winR;
    }

    public Line get(int index) {return lines.get(index);
    }

    public int getQuantity() {
        return lines.size();
    }

}
