package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Mock
    private AdminConfig adminConfig;

    @Test
    void shouldFetchTrelloBoards() {
        // Given
        List<TrelloBoardDto> mockBoards = List.of(
                new TrelloBoardDto("1", "Board name", List.of())
        );

        when(trelloClient.getTrelloBoards()).thenReturn(mockBoards);

        // When
        List<TrelloBoardDto> result = trelloService.fetchTrelloBoards();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo("1");
        assertThat(result.get(0).getName()).isEqualTo("Board name");
        verify(trelloClient, times(1)).getTrelloBoards();
    }

    @Test
    void shouldCreateTrelloCardAndSendEmail() {
        // Given
        TrelloCardDto cardDto = new TrelloCardDto("Test card", "Desc", "top", "123");
        CreatedTrelloCardDto createdCard = new CreatedTrelloCardDto("321", "Test card", "http://url", null);

        when(trelloClient.createNewCard(cardDto)).thenReturn(createdCard);
        when(adminConfig.getAdminMail()).thenReturn("test@admin.com");

        // When
        CreatedTrelloCardDto result = trelloService.createTrelloCard(cardDto);

        // Then
        assertThat(result).isEqualTo(createdCard);

        verify(emailService, times(1)).send(argThat(mail ->
                mail.getMailTo().equals("test@admin.com") &&
                        mail.getSubject().equals("Tasks: New Trello card") &&
                        mail.getMessage().contains("Test card")));
    }

    @Test
    void shouldNotSendEmailWhenCardIsNull() {
        // Given
        TrelloCardDto cardDto = new TrelloCardDto("Test card", "Desc", "top", "123");
        when(trelloClient.createNewCard(cardDto)).thenReturn(null);

        // When
        CreatedTrelloCardDto result = trelloService.createTrelloCard(cardDto);

        // Then
        assertThat(result).isNull();
        verify(emailService, never()).send(any());
    }
}
