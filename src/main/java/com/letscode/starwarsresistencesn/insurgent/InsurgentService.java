package com.letscode.starwarsresistencesn.insurgent;

import com.letscode.starwarsresistencesn.insurgent.model.*;
import java.util.Map;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InsurgentService {

    private InsurgentRepository repository;

    public Insurgent save(Insurgent insurgent) {
        return repository.save(insurgent);
    }

    public void updateLocalization(Integer id, Localization localization) {
        var insurgent = repository.findById(id)
                .orElseThrow();
        insurgent.setLocalization(localization);
        repository.save(insurgent);
    }

    @Transactional
    public void reportRenegade(ReportRenegade reportRenegade) {
        var insurgent = repository.findById(reportRenegade.getRenegadeId())
                .orElseThrow();

        insurgent.addReport(reportRenegade);
        repository.save(insurgent);
    }

    @Transactional
    public void exchange(Exchange exchange) {
        if (!exchange.isTheSameValueOfTheItemsByParticipant())
            throw new RuntimeException();

        var participant1 = repository.findById(exchange.getParticipant1Id())
                .orElseThrow();

        var participant2 = repository.findById(exchange.getParticipant2Id())
                .orElseThrow();

        participant1.removeResource(exchange.getParticipant1Resource());
        participant2.removeResource(exchange.getParticipant2Resource());

        participant1.addResource(exchange.getParticipant2Resource());
        participant2.addResource(exchange.getParticipant1Resource());

        repository.save(participant1);
        repository.save(participant2);
    }

    public Report report() {
        var reportProjection = repository.reportQuantities();
        var quantityOfInsurgent = reportProjection.getQuantityOfInsurgent();

        return new Report(
                reportProjection.getPercentageOfRenegade(),
                reportProjection.getPercentageOfInsurgent(),
                quantityResourceByInsurgent(quantityOfInsurgent),
                quantityLostByRenegade()
        );
    }

    private Map<Item, Double> quantityResourceByInsurgent(Integer totalInsurgents) {

        return repository.reportResourceByInsurgent().stream().map(it -> {

            var avg = it.getQuantity().doubleValue() / totalInsurgents.doubleValue();
            return Map.entry(it.getItem(), avg);

        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private int quantityLostByRenegade() {
        return repository.reportResourceByRenegade()
                .stream()
                .mapToInt(it -> it.getItem().getPoints() * it.getQuantity())
                .sum();
    }
}
