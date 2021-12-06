package com.letscode.starwarsresistencesn.insurgent.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Report {
    private Double percentageOfRenegades;
    private Double percentageOfInsurgents;
    private Map<Item, Double> averageAmountOfResources;
    private Integer lostPointsByRenegades;
}
