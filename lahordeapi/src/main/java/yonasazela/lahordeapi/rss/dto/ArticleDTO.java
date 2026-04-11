package yonasazela.lahordeapi.rss.dto;

import java.time.Instant;

/**
 * DTO representing a single RSS article aggregated from feeds.
 */
public record ArticleDTO(
        String id,
        String title,
        String link,
        String description,
        Instant publishedAt
) {}