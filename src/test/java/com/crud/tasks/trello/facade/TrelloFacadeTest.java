package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.validator.TrelloValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrelloFacadeTest {

    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloValidator trelloValidator;

    @Mock
    private TrelloMapper trelloMapper;

    @Test
    void shouldFetchEmptyList() {
        // Given
        List<TrelloListDto> trelloLists =
                List.of(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoards =
                List.of(new TrelloBoardDto("1", "test", trelloLists));

        List<TrelloList> mappedTrelloLists =
                List.of(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoards =
                List.of(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(List.of());
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(List.of());

        // When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        // Then
        assertNotNull(trelloBoardDtos);
        assertEquals(0, trelloBoardDtos.size());
    }

    @Test
    void shouldFetchTrelloBoards() {
        // Given
        List<TrelloListDto> trelloLists =
                List.of(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoards =
                List.of(new TrelloBoardDto("1", "test", trelloLists));

        List<TrelloList> mappedTrelloLists =
                List.of(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoards =
                List.of(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(trelloBoards);
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(mappedTrelloBoards);

        // When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        // Then
        assertNotNull(trelloBoardDtos);
        assertEquals(1, trelloBoardDtos.size());

        trelloBoardDtos.forEach(trelloBoardDto -> {

            assertEquals("1", trelloBoardDto.getId());
            assertEquals("test", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("test_list", trelloListDto.getName());
                assertFalse(trelloListDto.isClosed());
            });
        });

    }

    @Test
    void shouldReturnEmptyListWhenServiceReturnsNothing() {
        // Given
        when(trelloService.fetchTrelloBoards()).thenReturn(List.of());
        when(trelloMapper.mapToBoards(List.of())).thenReturn(List.of());
        when(trelloValidator.validateTrelloBoards(List.of())).thenReturn(List.of());
        when(trelloMapper.mapToBoardsDto(List.of())).thenReturn(List.of());

        // When
        List<TrelloBoardDto> result = trelloFacade.fetchTrelloBoards();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyListWhenMapperReturnsNull() {
        // Given
        List<TrelloBoardDto> input = List.of(new TrelloBoardDto("1", "test", List.of()));
        when(trelloService.fetchTrelloBoards()).thenReturn(input);
        when(trelloMapper.mapToBoards(input)).thenReturn(null);
        when(trelloMapper.mapToBoardsDto(List.of())).thenReturn(List.of());

        // When
        List<TrelloBoardDto> result = trelloFacade.fetchTrelloBoards();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFilterOutSomeBoards() {
        // Given
        List<TrelloListDto> trelloLists = List.of(new TrelloListDto("1", "list", false));
        TrelloBoardDto board1 = new TrelloBoardDto("1", "OK", trelloLists);
        TrelloBoardDto board2 = new TrelloBoardDto("2", "test", trelloLists);
        List<TrelloBoardDto> allBoards = List.of(board1, board2);

        List<TrelloBoard> mappedBoards = List.of(
                new TrelloBoard("1", "OK", List.of(new TrelloList("1", "list", false))),
                new TrelloBoard("2", "test", List.of(new TrelloList("1", "list", false)))
        );

        List<TrelloBoard> filteredBoards = List.of(mappedBoards.get(0));

        when(trelloService.fetchTrelloBoards()).thenReturn(allBoards);
        when(trelloMapper.mapToBoards(allBoards)).thenReturn(mappedBoards);
        when(trelloValidator.validateTrelloBoards(mappedBoards)).thenReturn(filteredBoards);
        when(trelloMapper.mapToBoardsDto(filteredBoards)).thenReturn(List.of(board1));

        // When
        List<TrelloBoardDto> result = trelloFacade.fetchTrelloBoards();

        // Then
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("OK", result.get(0).getName());
    }
}