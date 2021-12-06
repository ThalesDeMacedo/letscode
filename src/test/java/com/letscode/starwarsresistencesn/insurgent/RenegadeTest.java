package com.letscode.starwarsresistencesn.insurgent;

import com.letscode.starwarsresistencesn.insurgent.model.Insurgent;
import com.letscode.starwarsresistencesn.insurgent.model.ReportRenegade;
import com.letscode.starwarsresistencesn.insurgent.model.Resource;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RenegadeTest {

    @Spy
    private Insurgent insurgent;

    @Mock
    private InsurgentRepository repository;
    @InjectMocks
    private InsurgentService service;

    @Test
    @DisplayName("insurgente turned renegade")
    public void isRenegade(
            @Mock ReportRenegade reportRenegade1,
            @Mock ReportRenegade reportRenegade2,
            @Mock ReportRenegade reportRenegade3,
            @Mock Resource resource
    ){

        insurgent.addReport(reportRenegade1);
        insurgent.addReport(reportRenegade2);
        insurgent.addReport(reportRenegade3);

        Assertions.assertThrows(RuntimeException.class, () -> insurgent.removeResource(List.of(resource)));
    }

    @Test
    @DisplayName("insurgent not found")
    public void reportRenegadeNotFound(
            @Mock ReportRenegade reportRenegade
    ){

        when(
                repository.findById(anyInt())
        ).thenReturn(Optional.empty());

        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> service.reportRenegade(reportRenegade)
        );

        Mockito.verify(repository, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("report")
    public void reportRenegade(
            @Mock ReportRenegade reportRenegade,
            @Mock Insurgent insurgent
    ){

        when(
                repository.findById(anyInt())
        ).thenReturn(Optional.of(insurgent));

        service.reportRenegade(reportRenegade);

        Mockito.verify(repository, times(1)).findById(anyInt());
        Mockito.verify(insurgent, times(1)).addReport(reportRenegade);
        Mockito.verify(repository, times(1)).save(insurgent);
    }

}
