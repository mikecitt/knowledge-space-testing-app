INSERT INTO authority (name) VALUES
    ('ROLE_STUDENT'),
    ('ROLE_PROFESSOR');

INSERT INTO professor (id, first_name, last_name, email, password) VALUES
    (1, 'Petar', 'Petrović', 'petar@example.com', '$2a$04$P2R/ohGi2eYUJw02EEZaveX37jwcXb4E.RnwQo8MgP8EgNP0vjfN.');

INSERT INTO student (id, first_name, last_name, email, password, date_of_birth) VALUES
    (1000, 'Mika', 'Mikić', 'mika@example.com', '$2a$04$P2R/ohGi2eYUJw02EEZaveX37jwcXb4E.RnwQo8MgP8EgNP0vjfN.', '2000-10-05');

INSERT INTO user_authority (user_id, authority_id) VALUES
    (1, 2),
    (1000, 1);

INSERT INTO test VALUES
    (1, 'Test1', 60.0, CURRENT_DATE(), CURRENT_DATE() + INTERVAL 5 DAY, 1),
    (2, 'Test2', 120.0, CURRENT_DATE() + INTERVAL 10 DAY, CURRENT_DATE() + INTERVAL 14 DAY, 1);

INSERT INTO section VALUES
    (1, 'Section11', 1),
    (2, 'Section12', 1),
    (3, 'Section21', 2),
    (4, 'Section22', 2),
    (5, 'Section23', 2);

INSERT INTO item VALUES
    (1, NULL, 'Item111', 1),
    (2, NULL, 'Item112', 1),
    (3, NULL, 'Item113', 1),
    (4, NULL, 'Item114', 1),
    (5, NULL, 'Item115', 1),
    (6, NULL, 'Item116', 1),
    (7, NULL, 'Item117', 1),
    (8, NULL, 'Item118', 1),
    (9, NULL, 'Item121', 2),
    (10, NULL, 'Item122', 2),
    (11, NULL, 'Item123', 2),
    (12, NULL, 'Item124', 2),
    (13, NULL, 'Item211', 3),
    (14, NULL, 'Item212', 3),
    (15, NULL, 'Item213', 3),
    (16, NULL, 'Item214', 3),
    (17, NULL, 'Item215', 3),
    (18, NULL, 'Item216', 3),
    (19, NULL, 'Item217', 3),
    (20, NULL, 'Item221', 4),
    (21, NULL, 'Item222', 4),
    (22, NULL, 'Item223', 4),
    (23, NULL, 'Item224', 4),
    (24, NULL, 'Item231', 5),
    (25, NULL, 'Item232', 5),
    (26, NULL, 'Item233', 5);

INSERT INTO answer (points, text, item_id) VALUES
    (5.0, 'A', 1),
    (-5.0, 'B', 1),
    (10.0, 'C', 1),
    (-5.0, 'D', 1),
    (-5.0, 'E', 1),
    (10.0, 'F', 1),
    (5.0, 'A', 2),
    (-5.0, 'B', 2),
    (10.0, 'C', 2),
    (-5.0, 'D', 2),
    (-5.0, 'E', 2),
    (10.0, 'F', 2),
    (5.0, 'A', 3),
    (-5.0, 'B', 3),
    (10.0, 'C', 3),
    (-5.0, 'D', 3),
    (-5.0, 'E', 3),
    (10.0, 'F', 3),
    (5.0, 'A', 4),
    (-5.0, 'B', 4),
    (10.0, 'C', 4),
    (-5.0, 'D', 4),
    (-5.0, 'E', 4),
    (10.0, 'F', 4),
    (5.0, 'A', 5),
    (-5.0, 'B', 5),
    (10.0, 'C', 5),
    (-5.0, 'D', 5),
    (-5.0, 'E', 5),
    (10.0, 'F', 5),
    (5.0, 'A', 6),
    (-5.0, 'B', 6),
    (10.0, 'C', 6),
    (-5.0, 'D', 6),
    (-5.0, 'E', 6),
    (10.0, 'F', 6),
    (5.0, 'A', 7),
    (-5.0, 'B', 7),
    (10.0, 'C', 7),
    (-5.0, 'D', 7),
    (-5.0, 'E', 7),
    (10.0, 'F', 7),
    (5.0, 'A', 8),
    (-5.0, 'B', 8),
    (10.0, 'C', 8),
    (-5.0, 'D', 8),
    (-5.0, 'E', 8),
    (10.0, 'F', 8),
    (5.0, 'A', 9),
    (-5.0, 'B', 9),
    (10.0, 'C', 9),
    (-5.0, 'D', 9),
    (-5.0, 'E', 9),
    (10.0, 'F', 9),
    (5.0, 'A', 10),
    (-5.0, 'B', 10),
    (10.0, 'C', 10),
    (-5.0, 'D', 10),
    (-5.0, 'E', 10),
    (10.0, 'F', 10),
    (5.0, 'A', 11),
    (-5.0, 'B', 11),
    (10.0, 'C', 11),
    (-5.0, 'D', 11),
    (-5.0, 'E', 11),
    (10.0, 'F', 11),
    (5.0, 'A', 12),
    (-5.0, 'B', 12),
    (10.0, 'C', 12),
    (-5.0, 'D', 12),
    (-5.0, 'E', 12),
    (10.0, 'F', 12),
    (5.0, 'A', 13),
    (-5.0, 'B', 13),
    (10.0, 'C', 13),
    (-5.0, 'D', 13),
    (-5.0, 'E', 13),
    (10.0, 'F', 13),
    (5.0, 'A', 14),
    (-5.0, 'B', 14),
    (10.0, 'C', 14),
    (-5.0, 'D', 14),
    (-5.0, 'E', 14),
    (10.0, 'F', 14),
    (5.0, 'A', 15),
    (-5.0, 'B', 15),
    (10.0, 'C', 15),
    (-5.0, 'D', 15),
    (-5.0, 'E', 15),
    (10.0, 'F', 15),
    (5.0, 'A', 16),
    (-5.0, 'B', 16),
    (10.0, 'C', 16),
    (-5.0, 'D', 16),
    (-5.0, 'E', 16),
    (10.0, 'F', 16),
    (5.0, 'A', 17),
    (-5.0, 'B', 17),
    (10.0, 'C', 17),
    (-5.0, 'D', 17),
    (-5.0, 'E', 17),
    (10.0, 'F', 17),
    (5.0, 'A', 18),
    (-5.0, 'B', 18),
    (10.0, 'C', 18),
    (-5.0, 'D', 18),
    (-5.0, 'E', 18),
    (10.0, 'F', 18),
    (5.0, 'A', 19),
    (-5.0, 'B', 19),
    (10.0, 'C', 19),
    (-5.0, 'D', 19),
    (-5.0, 'E', 19),
    (10.0, 'F', 19),
    (5.0, 'A', 20),
    (-5.0, 'B', 20),
    (10.0, 'C', 20),
    (-5.0, 'D', 20),
    (-5.0, 'E', 20),
    (10.0, 'F', 20),
    (5.0, 'A', 21),
    (-5.0, 'B', 21),
    (10.0, 'C', 21),
    (-5.0, 'D', 21),
    (-5.0, 'E', 21),
    (10.0, 'F', 21),
    (5.0, 'A', 22),
    (-5.0, 'B', 22),
    (10.0, 'C', 22),
    (-5.0, 'D', 22),
    (-5.0, 'E', 22),
    (10.0, 'F', 22),
    (5.0, 'A', 23),
    (-5.0, 'B', 23),
    (10.0, 'C', 23),
    (-5.0, 'D', 23),
    (-5.0, 'E', 23),
    (10.0, 'F', 23),
    (5.0, 'A', 24),
    (-5.0, 'B', 24),
    (10.0, 'C', 24),
    (-5.0, 'D', 24),
    (-5.0, 'E', 24),
    (10.0, 'F', 24),
    (5.0, 'A', 25),
    (-5.0, 'B', 25),
    (10.0, 'C', 25),
    (-5.0, 'D', 25),
    (-5.0, 'E', 25),
    (10.0, 'F', 25),
    (5.0, 'A', 26),
    (-5.0, 'B', 26),
    (10.0, 'C', 26),
    (-5.0, 'D', 26),
    (-5.0, 'E', 26),
    (10.0, 'F', 26);
