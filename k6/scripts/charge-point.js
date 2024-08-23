import http from 'k6/http';
import { check, sleep } from 'k6';
import { randomIntBetween } from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';

export const options = {
    stages: [
        { duration: '10s', target: 300 },
        { duration: '1m', target: 300 },
        { duration: '10s', target: 0 },
    ],
};

export default function() {
    let userId = randomIntBetween(1, 1000);

    let res = http.patch(
        `http://host.docker.internal:8080/api/users/${userId}/points/charge`,
        JSON.stringify(randomIntBetween(1000, 100000)),
        {
            headers: { 'Content-Type': 'application/json' },
        }
    );

    check(res, { 'Charging point status is 200': (r) => r.status === 200 });
    sleep(1);
}