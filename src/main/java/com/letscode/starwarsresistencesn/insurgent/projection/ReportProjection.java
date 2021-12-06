package com.letscode.starwarsresistencesn.insurgent.projection;

import java.util.Optional;

public interface ReportProjection {
    Optional<Integer> getTotal();
    Optional<Integer> getRenegade();

    default Double getPercentageOfRenegade(){
        var total = getTotal()
                .orElse(0)
                .doubleValue();
        var renegade = getRenegade()
                .orElse(0)
                .doubleValue();

        return renegade / total * 100;
    }

    default Double getPercentageOfInsurgent(){
        return 100 - getPercentageOfRenegade();
    }

    default Integer getQuantityOfInsurgent(){
        var total = getTotal()
                .orElse(0);
        var renegade = getRenegade()
                .orElse(0);
        return total - renegade;
    }
}
