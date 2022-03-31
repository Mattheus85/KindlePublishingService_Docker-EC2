package com.amazon.ata.kindlepublishingservice.publishing;

import javax.inject.Inject;
import java.util.*;

public class BookPublishRequestManager {
    private final Queue<BookPublishRequest> bookPublishRequestQueue;

    @Inject
    public BookPublishRequestManager(Queue<BookPublishRequest> requestQueue) {
        this.bookPublishRequestQueue = requestQueue;
    }

    public void addBookPublishRequest(BookPublishRequest bookPublishRequest) {
        bookPublishRequestQueue.add(bookPublishRequest);
    }

    public BookPublishRequest getBookPublishRequestToProcess() {
        return bookPublishRequestQueue.poll();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookPublishRequestManager that = (BookPublishRequestManager) o;
        return bookPublishRequestQueue.equals(that.bookPublishRequestQueue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookPublishRequestQueue);
    }

    @Override
    public String toString() {
        return "BookPublishRequestManager{" +
                "bookPublishRequestList=" + bookPublishRequestQueue +
                '}';
    }
}
