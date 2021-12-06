package com.letscode.starwarsresistencesn.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letscode.starwarsresistencesn.insurgent.model.Exchange;
import com.letscode.starwarsresistencesn.insurgent.model.Insurgent;
import com.letscode.starwarsresistencesn.insurgent.model.Item;
import com.letscode.starwarsresistencesn.insurgent.model.Resource;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import util.Util;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExchangeControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void post_with_success_201() throws Exception {
        var resources1 = List.of(
                new Resource(Item.GUN, 4)
        );
        var resources2 = List.of(
                new Resource(Item.AMMUNITION, 3),
                new Resource(Item.WATER, 3),
                new Resource(Item.FOOD, 1)
        );

        var participant1 = createParticipant(resources1);
        var participant2 = createParticipant(resources2);

        var exchange = new Exchange(participant1, participant2);

        mvc.perform(
                post("/exchange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.asJsonString(exchange))
        ).andExpect(
                status().isNoContent()
        ).andDo(print());
    }

    private Exchange.Participant createParticipant(List<Resource> resources) throws Exception {
        var postInsurgent = InsurgentControllerTest.post_insurgent(mvc, resources)
                .andExpect(
                        status().isCreated()
                ).andReturn().getResponse().getContentAsString();
        var insurgent = mapper.readValue(postInsurgent, Insurgent.class);
        return new Exchange.Participant(insurgent.getId(), resources);
    }

}
