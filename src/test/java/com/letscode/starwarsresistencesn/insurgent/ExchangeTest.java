package com.letscode.starwarsresistencesn.insurgent;

import com.letscode.starwarsresistencesn.insurgent.model.Exchange;
import com.letscode.starwarsresistencesn.insurgent.model.Insurgent;
import com.letscode.starwarsresistencesn.insurgent.model.Resource;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ExchangeTest {

    @Mock
    private InsurgentRepository repository;
    @InjectMocks
    private InsurgentService service;

    @Test
    @DisplayName("exchange with different values")
    public void exchangeWithDifferentValues(
            @Mock Exchange exchange
    ){

        when(
                exchange.isTheSameValueOfTheItemsByParticipant()
        ).thenReturn(false);

        Assertions.assertThrows(RuntimeException.class, () -> service.exchange(exchange));

        verify(exchange, atLeastOnce()).isTheSameValueOfTheItemsByParticipant();
    }

    @Test
    @DisplayName("exchange with participant 1 not found")
    public void exchangeParticipant1NotFound(
            @Mock Exchange exchange
    ){
        var idParticipant1 = 1;

        when(
                exchange.isTheSameValueOfTheItemsByParticipant()
        ).thenReturn(true);
        when(
                exchange.getParticipant1Id()
        ).thenReturn(idParticipant1);
        when(
                repository.findById(idParticipant1)
        ).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> service.exchange(exchange));

        verify(exchange, atLeastOnce()).isTheSameValueOfTheItemsByParticipant();

        verify(repository, atLeastOnce()).findById(idParticipant1);
    }

    @Test
    @DisplayName("exchange with participant 2 not found")
    public void exchangeParticipant1NotFound(
            @Mock Exchange exchange,
            @Mock Insurgent participant1
    ){
        var idParticipant1 = 1;
        var idParticipant2 = 2;

        when(
                exchange.isTheSameValueOfTheItemsByParticipant()
        ).thenReturn(true);
        when(
                exchange.getParticipant1Id()
        ).thenReturn(idParticipant1);
        when(
                exchange.getParticipant2Id()
        ).thenReturn(idParticipant2);
        when(
                repository.findById(idParticipant1)
        ).thenReturn(Optional.of(participant1));
        when(
                repository.findById(idParticipant2)
        ).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> service.exchange(exchange));

        verify(exchange, atLeastOnce()).isTheSameValueOfTheItemsByParticipant();

        verify(repository, atLeastOnce()).findById(idParticipant1);
        verify(repository, atLeastOnce()).findById(idParticipant2);
    }

    @Test
    @DisplayName("exchange with success")
    public void exchangeWithSuccess(
            @Mock Exchange exchange,
            @Mock Insurgent participant1,
            @Mock Insurgent participant2,
            @Mock List<Resource> list1,
            @Mock List<Resource> list2
    ){
        var idParticipant1 = 1;
        var idParticipant2 = 2;

        when(
                exchange.isTheSameValueOfTheItemsByParticipant()
        ).thenReturn(true);
        when(
                exchange.getParticipant1Id()
        ).thenReturn(idParticipant1);
        when(
                exchange.getParticipant2Id()
        ).thenReturn(idParticipant2);
        when(
                repository.findById(idParticipant1)
        ).thenReturn(Optional.of(participant1));
        when(
                repository.findById(idParticipant2)
        ).thenReturn(Optional.of(participant2));
        when(
                exchange.getParticipant1Resource()
        ).thenReturn(list1);
        when(
                exchange.getParticipant2Resource()
        ).thenReturn(list2);

        service.exchange(exchange);

        verify(exchange, atLeastOnce()).isTheSameValueOfTheItemsByParticipant();

        verify(repository, atLeastOnce()).findById(idParticipant1);
        verify(repository, atLeastOnce()).findById(idParticipant2);

        verify(participant1, atLeastOnce()).removeResource(list1);
        verify(participant2, atLeastOnce()).removeResource(list2);

        verify(participant2, atLeastOnce()).addResource(list1);
        verify(participant1, atLeastOnce()).addResource(list2);

        verify(repository, atLeastOnce()).save(participant1);
        verify(repository, atLeastOnce()).save(participant2);
    }


}
