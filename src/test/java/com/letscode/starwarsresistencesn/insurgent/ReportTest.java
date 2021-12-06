package com.letscode.starwarsresistencesn.insurgent;

import com.letscode.starwarsresistencesn.insurgent.model.*;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReportTest {
    @Autowired
    private InsurgentService service;
    @Autowired
    private InsurgentRepository repository;

    @Test
    @DisplayName("report quantity just insurgent")
    public void reportQuantityInsurgent() {
        createInsurgent();
        createInsurgent();
        createInsurgent();

        var reportProjection = repository.reportQuantities();
        Assertions.assertTrue(reportProjection.getTotal().isPresent());
        Assertions.assertTrue(reportProjection.getRenegade().isPresent());
        Assertions.assertEquals(3, reportProjection.getTotal().get());
        Assertions.assertEquals(0, reportProjection.getRenegade().get());
    }

    @Test
    @DisplayName("report quantity")
    public void reportQuantity() {
        createInsurgent();
        createInsurgent();
        createInsurgent();

        createRenegade();
        createRenegade();

        var reportProjection = repository.reportQuantities();
        Assertions.assertTrue(reportProjection.getTotal().isPresent());
        Assertions.assertTrue(reportProjection.getRenegade().isPresent());
        Assertions.assertEquals(5, reportProjection.getTotal().get());
        Assertions.assertEquals(2, reportProjection.getRenegade().get());
    }

    @Test
    @DisplayName("report Resource By Insurgent")
    public void reportResourceByInsurgent() {

        createRenegade(service,
                List.of(
                        new Resource(Item.WATER, 1), new Resource(Item.FOOD, 1)
                )
        );

        createRenegade();

        var resource1 = new Resource(Item.GUN, 3);
        var resource2 = new Resource(Item.AMMUNITION, 3);

        createInsurgent(service,
                List.of(resource1, resource2)
        );

        var resource3 = new Resource(Item.WATER, 1);
        var resource4 = new Resource(Item.AMMUNITION, 3);

        createInsurgent(service,
                List.of(resource3, resource4)
        );

        var resource5 = new Resource(Item.WATER, 1);
        var resource6 = new Resource(Item.FOOD, 3);

        createInsurgent(service,
                List.of(resource5, resource6)
        );

        var map = new HashMap<Item, Integer>();
        compute(map, resource1);
        compute(map, resource2);
        compute(map, resource3);
        compute(map, resource4);
        compute(map, resource5);
        compute(map, resource6);

        var reportProjection = repository.reportResourceByInsurgent();
        Assertions.assertEquals(map.size(), reportProjection.size());

        reportProjection.forEach(it -> {
            var quantity = map.get(it.getItem());
            Assertions.assertNotNull(quantity);
            Assertions.assertEquals(quantity, it.getQuantity());
        });
    }

    @Test
    @DisplayName("report Resource By Renegade")
    public void reportResourceByRenegade() {

        createInsurgent(service,
                List.of(
                        new Resource(Item.WATER, 1), new Resource(Item.FOOD, 1)
                )
        );

        createInsurgent();

        var resource1 = new Resource(Item.GUN, 3);
        var resource2 = new Resource(Item.AMMUNITION, 3);

        createRenegade(service,
                List.of(resource1, resource2)
        );

        var resource3 = new Resource(Item.WATER, 1);
        var resource4 = new Resource(Item.AMMUNITION, 3);

        createRenegade(service,
                List.of(resource3, resource4)
        );

        var resource5 = new Resource(Item.WATER, 1);
        var resource6 = new Resource(Item.FOOD, 3);

        createRenegade(service,
                List.of(resource5, resource6)
        );

        var map = new HashMap<Item, Integer>();
        compute(map, resource1);
        compute(map, resource2);
        compute(map, resource3);
        compute(map, resource4);
        compute(map, resource5);
        compute(map, resource6);

        var reportProjection = repository.reportResourceByRenegade();
        Assertions.assertEquals(map.size(), reportProjection.size());

        reportProjection.forEach(it -> {
            var quantity = map.get(it.getItem());
            Assertions.assertNotNull(quantity);
            Assertions.assertEquals(quantity, it.getQuantity());
        });
    }

    @Test
    @DisplayName("report")
    public void report() {

        createInsurgent(service,
                List.of(
                        new Resource(Item.WATER, 1), new Resource(Item.FOOD, 1)
                )
        );

        createInsurgent(service,
                List.of(
                        new Resource(Item.GUN, 3), new Resource(Item.AMMUNITION, 5)
                )
        );

        createRenegade(service,
                List.of(
                        new Resource(Item.GUN, 3), new Resource(Item.AMMUNITION, 3))
        );

        createRenegade(service,
                List.of(
                        new Resource(Item.WATER, 1), new Resource(Item.AMMUNITION, 3)
                )
        );

        createRenegade(service,
                List.of(
                        new Resource(Item.WATER, 1), new Resource(Item.FOOD, 3)
                )
        );

        var report = service.report();
        Assertions.assertEquals(60, report.getPercentageOfRenegades());
        Assertions.assertEquals(40, report.getPercentageOfInsurgents());
        Assertions.assertEquals(37, report.getLostPointsByRenegades());

        Assertions.assertEquals(
                report.getAverageAmountOfResources().get(Item.AMMUNITION), 2.5
        );
        Assertions.assertEquals(
                report.getAverageAmountOfResources().get(Item.FOOD), 0.5
        );
        Assertions.assertEquals(
                report.getAverageAmountOfResources().get(Item.WATER), 0.5
        );
        Assertions.assertEquals(
                report.getAverageAmountOfResources().get(Item.GUN), 1.5
        );

    }

    private void createRenegade() {
        createRenegade(service, List.of());
    }

    public static void createRenegade(InsurgentService service, List<Resource> resources) {
        var insurgent = createInsurgent(service, resources);

        IntStream.range(0, 3).forEach(i -> {
            var report = new ReportRenegade(i);
            report.setRenegadeId(insurgent.getId());
            service.reportRenegade(report);
        });
    }

    private void createInsurgent() {
        createInsurgent(service, List.of());
    }

    public static Insurgent createInsurgent(InsurgentService service, List<Resource> resources) {
        var location = new Localization("sd", 0, 0);
        var insurgent = new Insurgent("name", 20, Insurgent.Genre.MALE, location);
        insurgent.addResource(resources);
        return service.save(insurgent);
    }

    private void compute(HashMap<Item, Integer> map, Resource resource) {
        map.compute(resource.getItem(), (k, v) -> v == null ? resource.getQuantity() : v + resource.getQuantity());
    }

}
