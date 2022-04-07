package com.amazon.ata.kindlepublishingservice.clients;

import com.amazon.ata.recommendationsservice.types.BookGenre;
import com.amazon.ata.recommendationsservice.types.BookRecommendation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class RecommendationsServiceCachingClientTest {

    @Mock
    private RecommendationsServiceClient serviceClient;

    @InjectMocks
    private RecommendationsServiceCachingClient cachingClient;

    @BeforeEach
    public void setup() {
        openMocks(this);
    }

    @Test
    public void getBookRecommendations_recommendationNotInCache_serviceClientCalled() {
        // GIVEN
        List<BookRecommendation> bookRecommendations = new ArrayList<>();
        BookRecommendation bookRec1 = new BookRecommendation("Book Title 1", "Author 1", "ASIN1");
        BookRecommendation bookRec2 = new BookRecommendation("Book Title 2", "Author 2", "ASIN2");
        bookRecommendations.add(bookRec1);
        bookRecommendations.add(bookRec2);
        when(serviceClient.getBookRecommendations(BookGenre.ACTION)).thenReturn(bookRecommendations);

        // WHEN
        List<BookRecommendation> result = cachingClient.getBookRecommendations(BookGenre.ACTION);

        // THEN
        assertEquals(result, bookRecommendations, "getBookRecommendations expected to return the list we made.");
        verify(serviceClient).getBookRecommendations(BookGenre.ACTION);
        verifyNoMoreInteractions(serviceClient);
    }

    @Test
    public void getBookRecommendations_recommendationIsInCache_serviceClientCalledOnlyOnce() {
        // GIVEN
        List<BookRecommendation> bookRecommendations = new ArrayList<>();
        BookRecommendation bookRec1 = new BookRecommendation("Book Title 1", "Author 1", "ASIN1");
        BookRecommendation bookRec2 = new BookRecommendation("Book Title 2", "Author 2", "ASIN2");
        bookRecommendations.add(bookRec1);
        bookRecommendations.add(bookRec2);
        when(serviceClient.getBookRecommendations(BookGenre.MYSTERY)).thenReturn(bookRecommendations);
        cachingClient.getBookRecommendations(BookGenre.MYSTERY);
        cachingClient.getBookRecommendations(BookGenre.ROMANCE);

        // WHEN
        List<BookRecommendation> result = cachingClient.getBookRecommendations(BookGenre.MYSTERY);

        // THEN
        assertEquals(result, bookRecommendations, "getBookRecommendations expected to return the list we made.");
        verify(serviceClient, times(2)).getBookRecommendations(any());
        verifyNoMoreInteractions(serviceClient);
    }
}
