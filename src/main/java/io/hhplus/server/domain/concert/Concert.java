package io.hhplus.server.domain.concert;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Concert {
    private Long id;
    private String title;
    private String casting;
}
