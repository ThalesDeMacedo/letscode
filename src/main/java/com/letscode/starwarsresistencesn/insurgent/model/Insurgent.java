package com.letscode.starwarsresistencesn.insurgent.model;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Insurgent {

    public enum Genre {
        MALE, FEMALE
    }

    @Id
    @GeneratedValue
    private Integer id;
    @NonNull
    @NotBlank
    private String name;
    @NonNull
    @NotNull
    private Integer age;
    @NonNull
    @NotNull
    private Genre genre;
    @NonNull
    @NotNull
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    private Localization localization;

    @Singular
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "id")
    private final List<ReportRenegade> reports = new ArrayList<>();

    @ElementCollection
    private final Map<Item, Integer> map = new HashMap<>();

    public void addReport(ReportRenegade reportRenegade) {
        reports.add(reportRenegade);
    }

    public Map<Item, Integer> getMap() {
        return Collections.unmodifiableMap(map);
    }

    public void removeResource(List<Resource> resources) {
        ifRenegadeThrowException();

        resources.forEach(it -> map.compute(
                it.getItem(), (k, v) -> {
                    if (v == null || v < it.getQuantity())
                        throw new RuntimeException();
                    return v - it.getQuantity();
                }
        ));

        map.values().removeIf(it -> it == 0);
    }

    public void addResource(List<Resource> resources) {
        ifRenegadeThrowException();

        resources.forEach(it -> map.compute(
                it.getItem(), (k, v) -> {
                    if (v == null)
                        return it.getQuantity();
                    return v + it.getQuantity();
                }
        ));
    }

    private void ifRenegadeThrowException() {
        if(reports.size() > 2)
            throw new RuntimeException();
    }

}
