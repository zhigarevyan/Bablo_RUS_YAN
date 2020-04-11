package entity;

import lombok.*;

@Data
@NoArgsConstructor
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
}
