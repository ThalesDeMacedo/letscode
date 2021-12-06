package com.letscode.starwarsresistencesn.insurgent.model;

import lombok.Getter;

@Getter
public enum Item {
    GUN(4), AMMUNITION(3), WATER(2), FOOD(1);

    private final int points;

    Item(int points) {
        this.points = points;
    }
}
