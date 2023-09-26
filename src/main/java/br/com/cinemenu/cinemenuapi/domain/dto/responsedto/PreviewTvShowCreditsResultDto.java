package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import java.util.List;

public record PreviewTvShowCreditsResultDto(
        List<CastMember> cast,
        List<CrewMember> crew,
        int id
) {
    public record CastMember(
            boolean adult,
            int gender,
            int id,
            String known_for_department,
            String name,
            String original_name,
            double popularity,
            String profile_path,
            String character,
            String credit_id,
            int order
    ) {
    }

    public record CrewMember(
            boolean adult,
            int gender,
            Long id,
            String known_for_department,
            String name,
            String original_name,
            double popularity,
            String profile_path,
            String department,
            String job,
            String credit_id
    ) {
    }
}
