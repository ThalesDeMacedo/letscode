package com.letscode.starwarsresistencesn.insurgent;

import com.letscode.starwarsresistencesn.insurgent.model.Insurgent;
import com.letscode.starwarsresistencesn.insurgent.model.Item;
import com.letscode.starwarsresistencesn.insurgent.model.Resource;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ResourceTest {

    @Spy
    private Insurgent insurgent;

    @Test
    @DisplayName("add resource")
    public void addResource(){

        var resources = List.of(
                new Resource(Item.GUN, 4),
                new Resource(Item.AMMUNITION, 3)
        );

        insurgent.addResource(resources);

        Assertions.assertEquals(2, insurgent.getMap().size());
    }

    @Test
    @DisplayName("remove resource")
    public void removeResource(){

        var resources = List.of(
                new Resource(Item.GUN, 4),
                new Resource(Item.AMMUNITION, 3)
        );

        insurgent.addResource(resources);
        insurgent.removeResource(resources);

        Assertions.assertEquals(0, insurgent.getMap().size());
    }

    @Test
    @DisplayName("remove resource and keep one")
    public void removeResourceKeepOne(){


        var resources = new ArrayList<Resource>();
        resources.add(new Resource(Item.GUN, 4));
        resources.add(new Resource(Item.AMMUNITION, 3));

        insurgent.addResource(resources);
        resources.remove(1);
        insurgent.removeResource(resources);

        Assertions.assertEquals(1, insurgent.getMap().size());
    }

    @Test
    @DisplayName("resource points")
    public void resourcePoints(){

        var quantity = 4;
        var points = new Resource(Item.GUN, quantity)
                .getPoints();

        var shouldBe = Item.GUN.getPoints() * quantity;
        Assertions.assertEquals(shouldBe, points);

    }
}
