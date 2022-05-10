package com.amazon.ata.kindlepublishingservice.activity;

import com.amazon.ata.coral.converter.CoralConverterUtil;
import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.models.PublishingStatusRecord;
import com.amazon.ata.kindlepublishingservice.models.requests.GetPublishingStatusRequest;
import com.amazon.ata.kindlepublishingservice.models.response.GetPublishingStatusResponse;

import javax.inject.Inject;
import java.util.List;

public class GetPublishingStatusActivity {
    private final PublishingStatusDao publishingStatusDao;

    @Inject
    public GetPublishingStatusActivity(PublishingStatusDao publishingStatusDao) {
        this.publishingStatusDao = publishingStatusDao;
    }

    public GetPublishingStatusResponse execute(GetPublishingStatusRequest publishingStatusRequest) {

        List<PublishingStatusItem> itemList = publishingStatusDao
                .getPublishingStatuses(publishingStatusRequest.getPublishingRecordId());

        return GetPublishingStatusResponse.builder()
                .withPublishingStatusHistory(CoralConverterUtil.convertList(itemList,
                        x ->
                                PublishingStatusRecord.builder()
                                        .withStatusMessage(x.getStatusMessage())
                                        .withBookId(x.getBookId())
                                        .withStatus(x.getStatus().toString())
                                        .build()
                ))
                .build();
    }
}
