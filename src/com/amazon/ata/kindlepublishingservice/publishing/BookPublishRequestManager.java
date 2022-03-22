package com.amazon.ata.kindlepublishingservice.publishing;

import javax.inject.Inject;
import java.util.*;

public class BookPublishRequestManager {
    Queue<BookPublishRequest> bookPublishRequestList;

    @Inject
    public BookPublishRequestManager(Queue<BookPublishRequest> bookPublishRequestList) {
        this.bookPublishRequestList = bookPublishRequestList;
    }

    public void addBookPublishRequest(BookPublishRequest bookPublishRequest) {
        bookPublishRequestList.add(bookPublishRequest);
    }

    public BookPublishRequest getBookPublishRequestToProcess() {
        return bookPublishRequestList.peek();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookPublishRequestManager that = (BookPublishRequestManager) o;
        return bookPublishRequestList.equals(that.bookPublishRequestList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookPublishRequestList);
    }

    @Override
    public String toString() {
        return "BookPublishRequestManager{" +
                "bookPublishRequestList=" + bookPublishRequestList +
                '}';
    }
}
