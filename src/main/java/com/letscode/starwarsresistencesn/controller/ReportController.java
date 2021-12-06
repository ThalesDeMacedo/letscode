package com.letscode.starwarsresistencesn.controller;

import com.letscode.starwarsresistencesn.insurgent.InsurgentService;
import com.letscode.starwarsresistencesn.insurgent.model.Report;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("report")
@AllArgsConstructor
public class ReportController {

    private InsurgentService service;

    @GetMapping
    public ResponseEntity<Report> report() {
        return ResponseEntity.ok(service.report());
    }

}
