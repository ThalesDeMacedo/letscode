package com.letscode.starwarsresistencesn.controller;

import com.letscode.starwarsresistencesn.insurgent.InsurgentService;
import com.letscode.starwarsresistencesn.insurgent.model.Insurgent;
import com.letscode.starwarsresistencesn.insurgent.model.Localization;
import com.letscode.starwarsresistencesn.insurgent.model.ReportRenegade;
import java.net.URI;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("insurgent")
public class InsurgentController {

    private InsurgentService service;

    @PostMapping
    public ResponseEntity<Insurgent> save(@RequestBody @Valid Insurgent insurgent) {
        var saved = service.save(insurgent);
        return ResponseEntity.created(URI.create("/insurgent/" + saved.getId())).body(saved);
    }

    @PatchMapping("{id}/localization")
    public ResponseEntity<?> updateLocation(
            @PathVariable Integer id,
            @RequestBody @Valid Localization localization) {

        service.updateLocalization(id, localization);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}/renegade")
    public ResponseEntity<?> reportRenegade(
            @PathVariable Integer id,
            @RequestBody @Valid ReportRenegade reportRenegade) {

        reportRenegade.setRenegadeId(id);
        service.reportRenegade(reportRenegade);
        return ResponseEntity.noContent().build();
    }

}
