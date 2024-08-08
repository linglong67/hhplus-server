package io.hhplus.server.domain.concert;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class Concert implements Serializable {
    private Long id;
    private String title;
    private String casting;
}
