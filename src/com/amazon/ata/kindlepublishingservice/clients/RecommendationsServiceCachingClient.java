package com.amazon.ata.kindlepublishingservice.clients;

import com.amazon.ata.recommendationsservice.types.BookGenre;
import com.amazon.ata.recommendationsservice.types.BookRecommendation;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class RecommendationsServiceCachingClient {
    private final LoadingCache<BookGenre, List<BookRecommendation>> loadingCache;

    @Inject
    public RecommendationsServiceCachingClient(RecommendationsServiceClient serviceClient) {
        loadingCache = CacheBuilder.newBuilder()
                .build(CacheLoader.from(serviceClient::getBookRecommendations));
    }

    public List<BookRecommendation> getBookRecommendations(BookGenre genre) {
        return loadingCache.getUnchecked(genre);
    }

    public ConcurrentMap<BookGenre, List<BookRecommendation>> getLoadingCacheMap() {
        return loadingCache.asMap();
    }
}
