package entity;

public class Line {

    String player1;
    String player2;
    String date;
    String score;
    String fora1;
    String fora2;
    String total1;
    String total2;
    String total;
    String winR1;
    String winR2;

    public Line(String date, String score, String fora1, String fora2, String total1, String total2, String total, String winR1, String winR2) {
        this.date = date;
        this.score = score;
        this.fora1 = fora1;
        this.fora2 = fora2;
        this.total1 = total1;
        this.total2 = total2;
        this.total = total;
        this.winR1 = winR1;
        this.winR2 = winR2;
    }

    public Line(String player2, String date, String score, String fora1, String fora2, String total1, String total2, String total) {
        this.player2 = player2;
        this.date = date;
        this.score = score;
        this.fora1 = fora1;
        this.fora2 = fora2;
        this.total1 = total1;
        this.total2 = total2;
        this.total = total;
    }

    public String getPlayer2() {
        return player2;
    }

    public String get(int index) {
        switch (index) {
            case 1:
                return date;
            case 2:
                return score;
            case 3:
                return fora1;
            case 4:
                return fora2;
            case 5:
                return total1;
            case 6:
                return total2;
            case 7:
                return total;
            case 8:
                return winR1;
            case 9:
                return winR2;
        }
        return null;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getFora1() {
        return fora1;
    }

    public void setFora1(String fora1) {
        this.fora1 = fora1;
    }

    public String getFora2() {
        return fora2;
    }

    public void setFora2(String fora2) {
        this.fora2 = fora2;
    }

    public String getTotal1() {
        return total1;
    }

    public void setTotal1(String total1) {
        this.total1 = total1;
    }

    public String getTotal2() {
        return total2;
    }

    public void setTotal2(String total2) {
        this.total2 = total2;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getWinR1() {
        return winR1;
    }

    public void setWinR1(String winR1) {
        this.winR1 = winR1;
    }

    public String getWinR2() {
        return winR2;
    }

    public void setWinR2(String winR2) {
        this.winR2 = winR2;
    }
}
