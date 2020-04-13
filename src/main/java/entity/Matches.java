package entity;

import lombok.Data;

@Data
public class Matches {
    private int id;
    private int player1;
    private int player2;
    private int result;
    private String date;
}
