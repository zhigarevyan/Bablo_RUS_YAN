package entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Player {
    private int id;
    private String name;

    public Player(String name) {
        this.name = name;
    }
}
