
insert into film_sessions (film_id, halls_id, start_time, end_time, price)
values
(1, 1, now(), now() + interval '2 hours', 500),
(2, 1, now() + interval '2 hour 15 minutes', now() + interval '4 hours 15 minutes', 500),
(3, 1, now() + interval '4 hours 20 minutes', now() + interval '5 hours 50 minutes', 500),
(1, 2, now() + interval '6 hours 15 minutes', now() + interval '7 hours 15 minutes', 1500),
(2, 2, now() + interval '7 hours 30 minutes', now() + interval '9 hours', 1500),
(3, 2, now() + interval '9 hours 15 minutes', now() + interval '10 hours', 1500);