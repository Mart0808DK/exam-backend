use exam_db;

INSERT INTO discipline (name, result_type)
VALUES ('100m Løb', 'Time'),
       ('Højdespring', 'Points'),
       ('Diskoskast', 'Distance');

INSERT INTO club (name, ranking, area)
VALUES ('Aarhus 1900', 1, 'Jylland'),
       ('Aarhus Fremad', 2, 'Jylland'),
       ('Aalborg Atletik', 3, 'Jylland'),
       ('København Atletik', 1, 'Sjælland'),
       ('Odense Atletik', 2, 'Fyn'),
       ('Helsingør Atletik', 3, 'Sjælland');

INSERT INTO participant (name, gender, age, club_id)
VALUES ('Anders Andersen', 'MALE', 25, 1),
       ('Bente Bentsen', 'FEMALE', 30, 2),
       ('Carsten Carstensen', 'MALE', 35, 3),
       ('Dorthe Dorthesen', 'FEMALE', 40, 4),
       ('Erik Eriksen', 'MALE', 45, 5),
       ('Fie Fiesdatter', 'FEMALE', 50, 6);

INSERT INTO participant_discipline (participant_id, discipline_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1),
       (6, 1),
       (1, 2),
       (5, 2),
       (6, 2),
       (2, 3),
       (3, 3),
       (4, 3),
       (5, 3),
       (6, 3);

INSERT INTO result (participant_id, discipline_id, date, result_type, result_value)
VALUES (1, 1, '2022-01-01 10:00:00', 'Time', 10),
       (2, 2, '2022-01-02 11:00:00', 'Points', 20),
       (3, 3, '2022-01-03 12:00:00', 'Distance', 30),
       (4, 1, '2022-01-04 13:00:00', 'Time', 40),
       (5, 2, '2022-01-05 14:00:00', 'Points', 50),
       (6, 3, '2022-01-06 15:00:00', 'Distance', 60);