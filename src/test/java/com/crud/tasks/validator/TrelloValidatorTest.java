package com.crud.tasks.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TrelloValidatorTest {

    private final TrelloValidator trelloValidator = new TrelloValidator();

    @Test
    void shouldLogTestMessageWhenCardNameContainsTest() {
        // Given
        TrelloCard card = new TrelloCard("test task", "desc", "top", "1");

        // When & Then
        trelloValidator.validateCard(card);
    }

    @Test
    void shouldLogProperMessageWhenCardNameDoesNotContainTest() {
        // Given
        TrelloCard card = new TrelloCard("production task", "desc", "top", "1");

        // When & Then
        trelloValidator.validateCard(card);
    }

    @Test
    void shouldFilterOutBoardsNamedTest() {
        // Given
        TrelloBoard board1 = new TrelloBoard("1", "Test", List.of());
        TrelloBoard board2 = new TrelloBoard("2", "Production", List.of());
        TrelloBoard board3 = new TrelloBoard("3", "test", List.of());
        List<TrelloBoard> boards = List.of(board1, board2, board3);

        // When
        List<TrelloBoard> result = trelloValidator.validateTrelloBoards(boards);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Production");
    }

    @Test
    void shouldReturnAllBoardsWhenNoneNamedTest() {
        // Given
        TrelloBoard board1 = new TrelloBoard("1", "Project A", List.of());
        TrelloBoard board2 = new TrelloBoard("2", "Project B", List.of());

        // When
        List<TrelloBoard> result = trelloValidator.validateTrelloBoards(List.of(board1, board2));

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(board1, board2);
    }

    @Test
    void shouldReturnEmptyListWhenAllBoardsAreNamedTest() {
        // Given
        TrelloBoard board1 = new TrelloBoard("1", "test", List.of());
        TrelloBoard board2 = new TrelloBoard("2", "TEST", List.of());

        // When
        List<TrelloBoard> result = trelloValidator.validateTrelloBoards(List.of(board1, board2));

        // Then
        assertThat(result).isEmpty();

    }
}
