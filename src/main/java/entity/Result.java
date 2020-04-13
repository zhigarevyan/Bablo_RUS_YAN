package entity;

import lombok.*;

@Data
public class Result {
    private int id; // айдишка нам тут походу не нужна.
    private String score;
    private String set1;
    private String set2;
    private String set3;
    private String set4;
    private String set5;
    private String set6;
    private String set7;

    public Result() {
        this.score = "null";
        this.set1 = "null";
        this.set2 = "null";
        this.set3 = "null";
        this.set4 = "null";
        this.set5 = "null";
        this.set6 = "null";
        this.set7 = "null";
    }

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

    public void setSet(int setNum, String result) {
        switch (setNum){
            case 1: this.set1 = result; break;
            case 2: this.set2 = result; break;
            case 3: this.set3 = result; break;
            case 4: this.set4 = result; break;
            case 5: this.set5 = result; break;
            case 6: this.set6 = result; break;
            case 7: this.set7 = result; break;
        }
    }

    public String getSet(int setNum) {
        String result = "";
        switch (setNum){
            case 1: result =  this.set1; break;
            case 2: result =  this.set2; break;
            case 3: result =  this.set3; break;
            case 4: result =  this.set4; break;
            case 5: result =  this.set5; break;
            case 6: result =  this.set6; break;
            case 7: result =  this.set7; break;
        }
        return result;
    }



}
