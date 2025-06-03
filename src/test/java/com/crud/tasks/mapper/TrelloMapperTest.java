package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TrelloMapperTest {

    private final TrelloMapper trelloMapper = new TrelloMapper();

    @Test
    void shouldMapToBoards() {
        // Given
        TrelloListDto listDto = new TrelloListDto("1", "Test List", false);
        TrelloBoardDto boardDto = new TrelloBoardDto("1", "Test Board", List.of(listDto));

        // When
        List<TrelloBoard> result = trelloMapper.mapToBoards(List.of(boardDto));

        // Then
        assertThat(result).hasSize(1);
        TrelloBoard board = result.get(0);
        assertThat(board.getId()).isEqualTo("1");
        assertThat(board.getName()).isEqualTo("Test Board");
        assertThat(board.getLists()).hasSize(1);
        assertThat(board.getLists().get(0).getName()).isEqualTo("Test List");
    }

    @Test
    void shouldMapToBoardsDto() {
        // Given
        TrelloList list = new TrelloList("2", "List Name", true);
        TrelloBoard board = new TrelloBoard("10", "Board Name", List.of(list));

        // When
        List<TrelloBoardDto> result = trelloMapper.mapToBoardsDto(List.of(board));

        // Then
        assertThat(result).hasSize(1);
        TrelloBoardDto boardDto = result.get(0);
        assertThat(boardDto.getId()).isEqualTo("10");
        assertThat(boardDto.getName()).isEqualTo("Board Name");
        assertThat(boardDto.getLists()).hasSize(1);
        assertThat(boardDto.getLists().get(0).isClosed()).isTrue();
    }

    @Test
    void shouldMapToList() {
        //Given
        TrelloListDto dto = new TrelloListDto("3", "List DTO", false);

        // When
        List<TrelloList> result = trelloMapper.mapToList(List.of(dto));

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo("3");
        assertThat(result.get(0).getName()).isEqualTo("List DTO");
        assertThat(result.get(0).isClosed()).isFalse();
    }

    @Test
    void shouldMapToListDto() {
        // Given
        TrelloList list = new TrelloList("4", "List", true);

        // When
        List<TrelloListDto> result = trelloMapper.mapToListDto(List.of(list));

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("List");
        assertThat(result.get(0).isClosed()).isTrue();
    }

    @Test
    void shouldMapToCardDto() {
        // Given
        TrelloCard card = new TrelloCard("Card Name", "Description", "top", "123");

        // When
        TrelloCardDto result = trelloMapper.mapToCardDto(card);

        // Then
        assertThat(result.getName()).isEqualTo("Card Name");
        assertThat(result.getDescription()).isEqualTo("Description");
        assertThat(result.getPos()).isEqualTo("top");
        assertThat(result.getListId()).isEqualTo("123");
    }

    @Test
    void shouldMapToCard() {
        // Given
        TrelloCardDto dto = new TrelloCardDto("Card DTO", "Desc", "bottom", "456");

        // When
        TrelloCard result = trelloMapper.mapToCard(dto);

        // Then
        assertThat(result.getName()).isEqualTo("Card DTO");
        assertThat(result.getDescription()).isEqualTo("Desc");
        assertThat(result.getPos()).isEqualTo("bottom");
        assertThat(result.getListId()).isEqualTo("456");
    }
}
