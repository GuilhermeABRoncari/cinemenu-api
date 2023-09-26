package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import java.util.List;
import java.util.Map;

public record PreviewMovieWatchProvidersResultDto(int id, Map<String, WatchProvider> results) {
    public record WatchProvider(String link, List<Flatrate> flatrate, List<Buy> buy, List<Free> free) {
    }

    public record Flatrate(String logoPath, int providerId, String providerName, int displayPriority) {
    }

    public record Buy(String logoPath, int providerId, String providerName, int displayPriority) {
    }

    public record Free(String logoPath, int providerId, String providerName, int displayPriority) {
    }
}


