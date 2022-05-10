package com.amazon.ata.kindlepublishingservice.converters;

import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.models.PublishingStatusRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Converters for PublishingStatus related objects.
 */
public class PublishingStatusConverter {

    private PublishingStatusConverter() {}

    /**
     * Converts the given {@link PublishingStatusItem} object to a {@link PublishingStatusRecord}.
     *
     * @param publishingStatusItem  The PublishingStatusItem to convert
     * @return                      The converted PublishingStatusRecord
     */
    public static PublishingStatusRecord toPublishingStatusRecord(PublishingStatusItem publishingStatusItem) {
        return PublishingStatusRecord.builder()
                .withStatus(publishingStatusItem.getStatus().toString())
                .withBookId(publishingStatusItem.getBookId())
                .withStatusMessage(publishingStatusItem.getStatusMessage())
                .build();
    }

    /**
     * Converts the given {@link PublishingStatusItem} list to a {@link PublishingStatusRecord} list.
     *
     * @param publishingStatusItemList  The PublishingStatusItem list to convert
     * @return                          The converted PublishingStatusRecord list
     */
    public static List<PublishingStatusRecord> toPublishingStatusRecordList
            (List<PublishingStatusItem> publishingStatusItemList) {

        List<PublishingStatusRecord> publishingStatusRecordList = new ArrayList<>();
        for (PublishingStatusItem item : publishingStatusItemList) {
            publishingStatusRecordList.add(toPublishingStatusRecord(item));
        }
        return publishingStatusRecordList;
    }
}
