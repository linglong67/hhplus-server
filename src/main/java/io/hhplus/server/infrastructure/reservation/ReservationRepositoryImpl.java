package io.hhplus.server.infrastructure.reservation;

import io.hhplus.server.domain.reservation.Reservation;
import io.hhplus.server.domain.reservation.ReservationRepository;
import io.hhplus.server.infrastructure.reservation.entity.ReservationEntity;
import io.hhplus.server.infrastructure.reservation.entity.ReservationTicketEntity;
import io.hhplus.server.infrastructure.reservation.repository.ReservationJpaRepository;
import io.hhplus.server.infrastructure.reservation.repository.ReservationTicketJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {
    private final ReservationJpaRepository reservationRepository;
    private final ReservationTicketJpaRepository reservationTicketRepository;

    @Override
    public Reservation reserve(Reservation reservation) {
        ReservationEntity entity = ReservationEntity.from(reservation);
        return ReservationEntity.toDomain(reservationRepository.save(entity));
    }

    @Override
    public Optional<Reservation> findById(long reservationId) {
        return reservationRepository.findById(reservationId).map(ReservationEntity::toDomain);
    }

    @Override
    public Reservation update(Reservation reservation) {
        ReservationEntity entity = ReservationEntity.from(reservation);
        return ReservationEntity.toDomain(reservationRepository.save(entity));
    }

    @Override
    public List<Reservation> findAllByStatusIsAndCreatedAtBefore(Reservation.Status status, LocalDateTime validationTime) {
        return reservationRepository.findAllByStatusIsAndCreatedAtBefore(status, validationTime)
                                    .stream()
                                    .map(ReservationEntity::toDomain)
                                    .toList();
    }

    @Override
    public List<Reservation.Ticket> issueTickets(List<Reservation.Ticket> tickets) {
        return reservationTicketRepository.saveAll(tickets.stream().map(ReservationTicketEntity::from).toList())
                                          .stream().map(ReservationTicketEntity::toDomain).toList();
    }

    @Override
    public List<Long> getConcertSeatIds(List<Long> reservationIds) {
        return reservationTicketRepository.findAllByReservationIdIn(reservationIds);
    }
}