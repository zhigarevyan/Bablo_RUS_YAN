package entity;

import lombok.Data;

import java.util.Date;

@Data
public class Matches {
    private int id;
    private int player1;
    private int player2;
    private int result;
    private Date date;
}
