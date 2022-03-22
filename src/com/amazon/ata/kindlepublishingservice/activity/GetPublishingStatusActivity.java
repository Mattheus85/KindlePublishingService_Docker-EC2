package com.amazon.ata.kindlepublishingservice.activity;

import com.amazon.ata.kindlepublishingservice.converters.PublishingStatusConverter;
import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.exceptions.KindlePublishingClientException;
import com.amazon.ata.kindlepublishingservice.exceptions.PublishingStatusNotFoundException;
import com.amazon.ata.kindlepublishingservice.models.requests.GetPublishingStatusRequest;
import com.amazon.ata.kindlepublishingservice.models.response.GetPublishingStatusResponse;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class GetPublishingStatusActivity {
    PublishingStatusDao publishingStatusDao;

    @Inject
    public GetPublishingStatusActivity(PublishingStatusDao publishingStatusDao) {
        this.publishingStatusDao = publishingStatusDao;
    }

    public GetPublishingStatusResponse execute(GetPublishingStatusRequest publishingStatusRequest) {
        List<PublishingStatusItem> itemList = new ArrayList<>();
        itemList = publishingStatusDao.getPublishingStatuses(publishingStatusRequest.getPublishingRecordId());

       /* try {
            itemList = publishingStatusDao.getPublishingStatuses(publishingStatusRequest.getPublishingRecordId());
        } catch (PublishingStatusNotFoundException e) {
            throw new KindlePublishingClientException(e.getMessage());
        }*/

        return GetPublishingStatusResponse.builder()
                .withPublishingStatusHistory(PublishingStatusConverter.toPublishingStatusRecordList(itemList))
                .build();
    }
}
