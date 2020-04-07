package entity;

import lombok.Data;

@Data
public class Result {
    private int id;
    private String score;
    private String set1;
    private String set2;
    private String set3;
    private String set4;
    private String set5;
    private String set6;
    private String set7;

    public Result(String score, String set1, String set2, String set3, String set4, String set5, String set6, String set7) {
        this.score = score;
        this.set1 = set1;
        this.set2 = set2;
        this.set3 = set3;
        this.set4 = set4;
        this.set5 = set5;
        this.set6 = set6;
        this.set7 = set7;
    }
}
