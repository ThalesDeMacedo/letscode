package com.letscode.starwarsresistencesn.integration;

import com.letscode.starwarsresistencesn.insurgent.InsurgentService;
import com.letscode.starwarsresistencesn.insurgent.ReportTest;
import com.letscode.starwarsresistencesn.insurgent.model.Item;
import com.letscode.starwarsresistencesn.insurgent.model.Resource;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReportControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private InsurgentService service;

    @Test
    public void get_report_without_Items() throws Exception {

        mvc.perform(
                get("/report")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$.percentageOfRenegades").value("NaN")
        ).andExpect(
                jsonPath("$.percentageOfInsurgents").value("NaN")
        ).andExpect(
                jsonPath("$.lostPointsByRenegades").value(0)
        );
    }

    @Test
    public void get_report_with_Items() throws Exception {

        ReportTest.createInsurgent(service,
                List.of(
                        new Resource(Item.WATER, 1), new Resource(Item.FOOD, 1)
                )
        );

        ReportTest.createInsurgent(service,
                List.of(
                        new Resource(Item.GUN, 3), new Resource(Item.AMMUNITION, 5)
                )
        );

        ReportTest.createRenegade(service,
                List.of(
                        new Resource(Item.GUN, 3), new Resource(Item.AMMUNITION, 3))
        );

        ReportTest.createRenegade(service,
                List.of(
                        new Resource(Item.WATER, 1), new Resource(Item.AMMUNITION, 3)
                )
        );

        ReportTest.createRenegade(service,
                List.of(
                        new Resource(Item.WATER, 1), new Resource(Item.FOOD, 3)
                )
        );

        mvc.perform(
                get("/report")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$.percentageOfRenegades").value(60.0)
        ).andExpect(
                jsonPath("$.percentageOfInsurgents").value(40.0)
        ).andExpect(
                jsonPath("$.lostPointsByRenegades").value(37)
        );
    }

}
