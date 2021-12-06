package com.letscode.starwarsresistencesn.insurgent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Resource {
    private Item item;
    private int quantity;

    @JsonIgnore
    public int getPoints() {
        return item.getPoints() * quantity;
    }
}