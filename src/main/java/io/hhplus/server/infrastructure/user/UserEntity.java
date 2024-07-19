package io.hhplus.server.infrastructure.user;

import io.hhplus.server.domain.user.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`user`")
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer point;

    public static UserEntity from(User user) {
        UserEntity entity = new UserEntity();
        entity.id = user.getId();
        entity.name = user.getName();
        entity.point = user.getPoint();

        return entity;
    }

    public static User toDomain(UserEntity entity) {
        return User.builder()
                   .id(entity.id)
                   .name(entity.name)
                   .point(entity.point)
                   .build();
    }
}

