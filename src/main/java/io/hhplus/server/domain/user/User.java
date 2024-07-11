package io.hhplus.server.domain.user;

import lombok.Getter;

@Getter
public class User {
    private Long id;
    private String name;
    private Integer point;

    public void chargePoint(int amount) {
        this.point += amount;
    }

    public void usePoint(int amount) {
        this.point -= amount;
    }
}
