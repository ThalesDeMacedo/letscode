package com.letscode.starwarsresistencesn.insurgent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Exchange {
    private Participant participant1;
    private Participant participant2;

    @JsonIgnore
    public boolean isTheSameValueOfTheItemsByParticipant() {
        return Objects.equals(participant1.getPoints(), participant2.getPoints());
    }

    @JsonIgnore
    public int getParticipant1Id() {
        return participant1.getId();
    }

    @JsonIgnore
    public int getParticipant2Id() {
        return participant2.getId();
    }

    @JsonIgnore
    public List<Resource> getParticipant1Resource() {
        return participant1.getResources();
    }

    @JsonIgnore
    public List<Resource> getParticipant2Resource() {
        return participant2.getResources();
    }

    @Getter
    @AllArgsConstructor
    public static class Participant {
        private Integer id;
        private List<Resource> resources;

        @JsonIgnore
        public Integer getPoints() {
            return resources.stream()
                    .mapToInt(Resource::getPoints)
                    .sum();

        }
    }

}
