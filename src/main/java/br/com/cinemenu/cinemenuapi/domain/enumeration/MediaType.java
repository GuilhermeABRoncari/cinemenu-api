package br.com.cinemenu.cinemenuapi.domain.enumeration;

import io.micrometer.common.util.StringUtils;

public enum MediaType {
    TV,
    MOVIE,
    PERSON;

    public static MediaType fromString(String mediaType) {
        if (StringUtils.isNotBlank(mediaType)) {
            try {
                return MediaType.valueOf(mediaType.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("MediaType " + mediaType + " does not exist.", ex);
            }
        }
        throw new IllegalArgumentException("MediaType must not be null");
    }
}
