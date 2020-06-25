package entity;

public class WinL {
    private int id;
    private String date;
    private int leagueid;
    private double winL;

    public WinL() {
    }

    public WinL(int id, String date, int leagueid, double winL) {
        this.id = id;
        this.date = date;
        this.leagueid = leagueid;
        this.winL = winL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLeagueid() {
        return leagueid;
    }

    public void setLeagueid(int leagueid) {
        this.leagueid = leagueid;
    }

    public double getWinL() {
        return winL;
    }

    public void setWinL(double winL) {
        this.winL = winL;
    }
}
