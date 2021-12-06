package com.letscode.starwarsresistencesn.integration;

import com.letscode.starwarsresistencesn.insurgent.model.Insurgent;
import com.letscode.starwarsresistencesn.insurgent.model.Item;
import com.letscode.starwarsresistencesn.insurgent.model.Localization;
import com.letscode.starwarsresistencesn.insurgent.model.Resource;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import util.Util;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InsurgentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void post_with_success_201() throws Exception {
        var resources = List.of(new Resource(Item.GUN, 5));
        post_insurgent(mvc, resources).andExpect(
                status().isCreated()
        );
    }

    public static ResultActions post_insurgent(MockMvc mvc, List<Resource> resources) throws Exception {
        var localization = new Localization("test", 0, 0);

        var insurgent = new Insurgent("name", 20, Insurgent.Genre.MALE, localization);
        insurgent.addResource(resources);

        return mvc.perform(
                post("/insurgent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.asJsonString(insurgent))
        );
    }

    @Test
    public void post_without_fields_400() throws Exception {

        mvc.perform(
                post("/insurgent")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isBadRequest()
        ).andDo(print());
    }

    @Test
    public void patch_localization_success_204() throws Exception {
        var localization = new Localization("test", 0, 0);

        var insurgent = new Insurgent("name", 20, Insurgent.Genre.MALE, localization);

        insurgent.addResource(
                List.of(new Resource(Item.GUN, 5))
        );


        mvc.perform(
                post("/insurgent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.asJsonString(insurgent))
        ).andExpect(
                status().isCreated()
        );

        var newLocalization = new Localization("test2", 1, 1);

        mvc.perform(
                patch("/insurgent/1/localization")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.asJsonString(newLocalization))
        ).andExpect(
                status().isNoContent()
        );
    }
}
