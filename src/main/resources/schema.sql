CREATE TABLE "user"
(
    id      BIGINT AUTO_INCREMENT,
    name    VARCHAR(50),
    point   INT,
    version INT,
    PRIMARY KEY (id)
);

CREATE TABLE queue
(
    id           BIGINT AUTO_INCREMENT,
    user_id      BIGINT,
    token        VARCHAR(100),
    status       VARCHAR(20),
    activated_at DATETIME,
    created_at   DATETIME,
    updated_at   DATETIME,
    PRIMARY KEY (id)
);

CREATE TABLE place
(
    id   BIGINT AUTO_INCREMENT,
    name VARCHAR(100),
    PRIMARY KEY (id)
);

CREATE TABLE seat
(
    id       BIGINT AUTO_INCREMENT,
    place_id BIGINT,
    seat_no  INT,
    PRIMARY KEY (id)
);

CREATE TABLE concert
(
    id      BIGINT AUTO_INCREMENT,
    title   VARCHAR(100),
    casting VARCHAR(100),
    PRIMARY KEY (id)
);

CREATE TABLE concert_schedule
(
    id               BIGINT AUTO_INCREMENT,
    concert_id       BIGINT,
    place_id         BIGINT,
    concert_datetime DATETIME,
    PRIMARY KEY (id)
);

CREATE TABLE concert_seat
(
    id                  BIGINT AUTO_INCREMENT,
    concert_schedule_id BIGINT,
    seat_id             BIGINT,
    status              VARCHAR(20),
    price               BIGINT,
    version             INT,
    PRIMARY KEY (id)
);

CREATE TABLE reservation_ticket
(
    id              BIGINT AUTO_INCREMENT,
    reservation_id  BIGINT,
    concert_seat_id BIGINT,
    seat_id         BIGINT,
    seat_no         INT,
    price           INT,
    PRIMARY KEY (id)
);

CREATE TABLE reservation
(
    id                  BIGINT AUTO_INCREMENT,
    user_id             BIGINT,
    concert_schedule_id BIGINT,
    place_id            BIGINT,
    concert_title       VARCHAR(100),
    concert_casting     VARCHAR(100),
    concert_datetime    DATETIME,
    place_name          VARCHAR(100),
    total_price         INT,
    status              VARCHAR(20),
    created_at          DATETIME,
    created_by          VARCHAR(50),
    updated_by          VARCHAR(50),
    updated_at          DATETIME,
    PRIMARY KEY (id)
);

CREATE TABLE payment
(
    id             BIGINT AUTO_INCREMENT,
    user_id        BIGINT,
    reservation_id BIGINT,
    status         VARCHAR(20),
    paid_price     INT,
    created_at     DATETIME,
    updated_at     DATETIME,
    PRIMARY KEY (id)
);