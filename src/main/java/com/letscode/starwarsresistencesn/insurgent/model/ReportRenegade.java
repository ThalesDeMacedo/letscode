package com.letscode.starwarsresistencesn.insurgent.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportRenegade {
    @Id
    @GeneratedValue
    private Integer id;
    @NonNull @NotNull
    private Integer reporterId;
    @Setter
    private Integer renegadeId;
}
