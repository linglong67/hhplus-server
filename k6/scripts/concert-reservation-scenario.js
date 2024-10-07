import http from 'k6/http';
import { check, sleep } from 'k6';
import { randomIntBetween, randomItem } from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';

export let options = {
    scenarios: {
        concert_reservation_scenario: {
            executor: 'ramping-arrival-rate',
            timeUnit: '10s',
            preAllocatedVUs: 100,
            maxVUs: 200,
            stages: [
                { duration: '1m', target: 50 },
                { duration: '30s', target: 100 },
                { duration: '30s', target: 0 },
            ],
            exec: 'reservation_scenario',
        }
    }
};

export function reservation_scenario() {
    let concert = getConcerts();
    sleep(1);

    let concertDate = getAvailableDates(concert['concertId']);
    sleep(1);

    let concertSeat = getAvailableSeats(concertDate['concertId'], concertDate['concertScheduleId']);
    sleep(1);

    let concertSeatIds = [];
    concertSeatIds.push(concertSeat['concertSeatId']);
    let userId = randomIntBetween(1, 1000000);
    let reservation = reserveSeat(userId, concertSeat.concertScheduleId, concertSeatIds);
    sleep(1);

    payment(userId, reservation['reservationId'], randomIntBetween(10000, 100000));
    sleep(1);
}

function getConcerts() {
    let res = http.get(
        'http://host.docker.internal:8080/api/concerts',
    );

    check(res, {'Getting concerts is status 200': (r) => r.status === 200});

    return randomItem(res.json());
}

function getAvailableDates(concertId) {
    let res = http.get(
        `http://host.docker.internal:8080/api/concerts/${concertId}/available-dates`,
    );

    check(res, {'Getting concert dates is status 200': (r) => r.status === 200});

    return randomItem(res.json());
}

function getAvailableSeats(concertId, concertScheduleId) {
    let res = http.get(
        `http://host.docker.internal:8080/api/concerts/${concertId}/schedules/${concertScheduleId}/available-seats`,
    );

    check(res, {'Getting concert seats is status 200': (r) => r.status === 200});

    return randomItem(res.json());
}

function reserveSeat(userId, concertScheduleId, concertSeatIds) {
    let req = {
        userId: userId,
        concertScheduleId: concertScheduleId,
        concertSeatIds: concertSeatIds
    };

    let res = http.post(
        `http://host.docker.internal:8080/api/reservations`,
        JSON.stringify(req),
        {
            headers: { 'Content-Type': 'application/json' },
        }
    );

    check(res, {'Seat reservation is status 200': (r) => r.status === 200});

    return res.json();
}

function payment(userId, reservationId, paidPrice) {
    let req = {
        userId: userId,
        reservationId: 14,
        paidPrice: paidPrice
    };

    let res = http.post(
        `http://host.docker.internal:8080/api/payments`,
        JSON.stringify(req),
        {
            headers: {
                'Content-Type': 'application/json',
                'Queue-Token': 'test:1724364684868'
            },
        }
    );

    check(res, {'Payment is status 200': (r) => r.status === 200});

    return res.json();
}