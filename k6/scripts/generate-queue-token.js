import http from 'k6/http';
import { check, sleep } from 'k6';
import { randomIntBetween } from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';

export const options = {
    stages: [
        { duration: '5s', target: 2000 },
        { duration: '1m', target: 2000 },
        { duration: '15s', target: 0 },
    ],
};

export default function() {
    let userId = randomIntBetween(1, 1000000);

    let res = http.post(
        'http://host.docker.internal:8080/api/tokens',
        JSON.stringify(userId),
        {
            headers: { 'Content-Type': 'application/json' },
        }
    );

    check(res, { 'Generating Queue Tokens is status 200': (r) => r.status === 200 });
    sleep(1);
}