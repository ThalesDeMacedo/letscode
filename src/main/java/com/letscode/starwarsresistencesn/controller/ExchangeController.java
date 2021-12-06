package com.letscode.starwarsresistencesn.controller;

import com.letscode.starwarsresistencesn.insurgent.InsurgentService;
import com.letscode.starwarsresistencesn.insurgent.model.Exchange;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("exchange")
@AllArgsConstructor
public class ExchangeController {

    private InsurgentService insurgentService;

    @PostMapping
    public ResponseEntity<?> exchange(@RequestBody @Valid Exchange exchange){
        insurgentService.exchange(exchange);
        return ResponseEntity.noContent().build();
    }
}
