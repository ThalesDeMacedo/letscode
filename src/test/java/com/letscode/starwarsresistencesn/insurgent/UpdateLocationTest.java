package com.letscode.starwarsresistencesn.insurgent;

import com.letscode.starwarsresistencesn.insurgent.model.Insurgent;
import com.letscode.starwarsresistencesn.insurgent.model.Localization;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UpdateLocationTest {

    @Mock
    private InsurgentRepository repository;
    @InjectMocks
    private InsurgentService service;

    @Test
    @DisplayName("insurgent not found")
    public void insurgenteNotFound(
            @Mock Localization localization
    ) {
        when(
                repository.findById(anyInt())
        ).thenReturn(Optional.empty());

        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> service.updateLocalization(1, localization)
        );

        Mockito.verify(repository, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("insurgent updated")
    public void insurgenteUpdated(
            @Mock Localization localization,
            @Mock Insurgent insurgent
    ) {
        when(
                repository.findById(anyInt())
        ).thenReturn(Optional.of(insurgent));

        service.updateLocalization(1, localization);

        Mockito.verify(repository, times(1)).findById(anyInt());
        Mockito.verify(insurgent, times(1)).setLocalization(localization);
        Mockito.verify(repository, times(1)).save(insurgent);
    }


}
