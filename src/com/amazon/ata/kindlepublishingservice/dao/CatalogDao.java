package com.amazon.ata.kindlepublishingservice.dao;

import com.amazon.ata.kindlepublishingservice.dynamodb.models.CatalogItemVersion;
import com.amazon.ata.kindlepublishingservice.exceptions.BookNotFoundException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

import javax.inject.Inject;
import java.util.List;

public class CatalogDao {

    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates a new CatalogDao object.
     *
     * @param dynamoDbMapper The {@link DynamoDBMapper} used to interact with the catalog table.
     */
    @Inject
    public CatalogDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Returns the latest version of the book from the catalog corresponding to the specified book id.
     * Throws a BookNotFoundException if the latest version is not active or no version is found.
     *
     * @param bookId    Id associated with the book.
     * @return          The corresponding {@link CatalogItemVersion} from the catalog table.
     */
    public CatalogItemVersion getBookFromCatalog(String bookId) {
        CatalogItemVersion book = getLatestVersionOfBook(bookId);

        if (book == null || book.isInactive()) {
            throw new BookNotFoundException(String.format("No book found for id: %s", bookId));
        }

        return book;
    }

    /**
     * Returns the latest version of the book from the catalog corresponding to the specified book id.
     * Returns null if no version is found.
     *
     * @param bookId    Id associated with the book.
     * @return          The corresponding {@link CatalogItemVersion} from the catalog table.
     */
    private CatalogItemVersion getLatestVersionOfBook(String bookId) {
        CatalogItemVersion book = new CatalogItemVersion();
        book.setBookId(bookId);

        DynamoDBQueryExpression<CatalogItemVersion> queryExpression = new DynamoDBQueryExpression<CatalogItemVersion>()
                .withHashKeyValues(book)
                .withScanIndexForward(false)
                .withLimit(1);

        List<CatalogItemVersion> results = dynamoDbMapper.query(CatalogItemVersion.class, queryExpression);
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    /**
     * Performs a soft remove (sets the inactive property to TRUE)
     * of the {@link CatalogItemVersion} corresponding to the bookId.
     *
     * @param bookId    Id associated with the book to remove.
     * @return          The corresponding {@link CatalogItemVersion} which has been removed.
     */
    public CatalogItemVersion removeBookFromCatalog(String bookId) {
        CatalogItemVersion book = getBookFromCatalog(bookId);
        book.setInactive(true);
        dynamoDbMapper.save(book);
        return book;
    }

    /**
     * Throws {@link BookNotFoundException} if the book does not exist in the catalog table.
     *
     * @param bookId Id associated with the book.
     */
    public void validateBookExists(String bookId) {
        if (getLatestVersionOfBook(bookId) == null) {
            throw new BookNotFoundException(String.format("No book found for id: %s", bookId));
        }
    }
}
