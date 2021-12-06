package com.letscode.starwarsresistencesn.insurgent.projection;

import com.letscode.starwarsresistencesn.insurgent.model.Item;

public interface ResourceProjection {
    Item getItem();
    Integer getQuantity();
}
