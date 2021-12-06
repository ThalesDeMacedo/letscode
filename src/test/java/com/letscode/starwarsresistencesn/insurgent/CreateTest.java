package com.letscode.starwarsresistencesn.insurgent;

import com.letscode.starwarsresistencesn.insurgent.model.Insurgent;
import com.letscode.starwarsresistencesn.insurgent.model.Localization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CreateTest {

    @Mock
    private InsurgentRepository repository;
    @InjectMocks
    private InsurgentService service;

    @Test
    @DisplayName("insurgent Creation")
    public void InsurgenteCreation(
            @Mock Localization localization
    ) {
        var name = "name";
        var insurgent = new Insurgent(name, 20, Insurgent.Genre.MALE, localization);

        Assertions.assertEquals(insurgent.getName(), name);
        Assertions.assertEquals(insurgent.getLocalization(), localization);
    }

    @Test
    @DisplayName("insurgent save")
    public void InsurgenteSave(
            @Mock Insurgent insurgent
    ) {
        when(
                repository.save(insurgent)
        ).thenReturn(insurgent);

        var saved = service.save(insurgent);

        Assertions.assertEquals(insurgent, saved);
        Mockito.verify(repository, times(1)).save(insurgent);
    }


}
