package entity;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//@Data
@Data
@RequiredArgsConstructor
public class Player {
    private int id;
    private String name;

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }

    public Player(String name) {
        this.name = name;
    }
}
