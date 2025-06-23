INSERT INTO deck (name) VALUES ('English Vocabulary');
INSERT INTO deck (name) VALUES ('Programming Terms');

INSERT INTO flashcard (question, answer, interval_days, next_review_date, deck_id) VALUES
                                                                                       ('What is Apple?', 'Яблоко', 1, CURRENT_DATE, 1),
                                                                                       ('What is Table?', 'Стол', 2, CURRENT_DATE + INTERVAL '2 days', 1),
                                                                                       ('What is Java?', 'A programming language', 1, CURRENT_DATE, 2);