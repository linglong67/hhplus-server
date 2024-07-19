package io.hhplus.server.infrastructure.reservation.entity;

import io.hhplus.server.domain.reservation.Reservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "reservation_ticket")
@NoArgsConstructor
public class ReservationTicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reservationId;

    private Long concertSeatId;

    private Long seatId;

    private Integer seatNo;
    private Integer price;

    public static ReservationTicketEntity from(Reservation.Ticket reservationTicket) {
        ReservationTicketEntity entity = new ReservationTicketEntity();
        entity.id = reservationTicket.getId();
        entity.reservationId = reservationTicket.getReservationId();
        entity.concertSeatId = reservationTicket.getConcertSeatId();
        entity.seatId = reservationTicket.getSeatId();
        entity.seatNo = reservationTicket.getSeatNo();
        entity.price = reservationTicket.getPrice();

        return entity;
    }

    public static Reservation.Ticket toDomain(ReservationTicketEntity entity) {
        return Reservation.Ticket.builder()
                                 .id(entity.id)
                                 .reservationId(entity.reservationId)
                                 .concertSeatId(entity.concertSeatId)
                                 .seatId(entity.seatId)
                                 .seatNo(entity.seatNo)
                                 .price(entity.price)
                                 .build();
    }
}
