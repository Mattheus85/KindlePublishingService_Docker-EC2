package com.amazon.ata.kindlepublishingservice.publishing;

import com.amazon.ata.kindlepublishingservice.dao.CatalogDao;
import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.CatalogItemVersion;
import com.amazon.ata.kindlepublishingservice.enums.PublishingRecordStatus;
import com.amazon.ata.kindlepublishingservice.exceptions.BookNotFoundException;

import javax.inject.Inject;

public final class BookPublishTask implements Runnable {
    private final BookPublishRequestManager manager;
    private final PublishingStatusDao statusDao;
    private final CatalogDao catalogDao;

    @Inject
    public BookPublishTask(BookPublishRequestManager manager, PublishingStatusDao statusDao,
                           CatalogDao catalogDao) {
        this.manager = manager;
        this.statusDao = statusDao;
        this.catalogDao = catalogDao;
    }

    @Override
    public void run() {
        BookPublishRequest request = manager.getBookPublishRequestToProcess();

        int counter = 0;
        while (request == null && counter < 30) {
            try {
                Thread.sleep(500);
                request = manager.getBookPublishRequestToProcess();
                counter++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        statusDao.setPublishingStatus(request.getPublishingRecordId(),
                PublishingRecordStatus.IN_PROGRESS,
                request.getBookId());

        KindleFormattedBook formattedBook = KindleFormatConverter.format(request);

        CatalogItemVersion book = catalogDao.createOrUpdateBook(formattedBook);
        try {
            statusDao.setPublishingStatus(request.getPublishingRecordId(), PublishingRecordStatus.SUCCESSFUL,
                    book.getBookId(), "Publishing status: SUCCESS");
        } catch (BookNotFoundException e) {
            statusDao.setPublishingStatus(request.getPublishingRecordId(), PublishingRecordStatus.FAILED,
                    book.getBookId(), "The book ID " + request.getBookId() + " was not found.");
        }
    }
}
