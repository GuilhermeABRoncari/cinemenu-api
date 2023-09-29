package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;
import java.util.Map;

public record PreviewTvShowWatchProvidersResultDto(int id, Map<String, WatchProvider> results) {
    public record WatchProvider(String link, List<Flatrate> flatrate, List<Buy> buy, List<Free> free) {
    }

    public record Flatrate(
            @JsonAlias("logo_path")
            String logoPath,
            @JsonAlias("provider_id")
            int providerId,
            @JsonAlias("provider_name")
            String providerName,
            @JsonAlias("display_priority")
            int displayPriority) {
    }

    public record Buy(
            @JsonAlias("logo_path")
            String logoPath,
            @JsonAlias("provider_id")
            int providerId,
            @JsonAlias("provider_name")
            String providerName,
            @JsonAlias("display_priority")
            int displayPriority) {
    }

    public record Free(
            @JsonAlias("logo_path")
            String logoPath,
            @JsonAlias("provider_id")
            int providerId,
            @JsonAlias("provider_name")
            String providerName,
            @JsonAlias("display_priority")
            int displayPriority) {
    }
}


