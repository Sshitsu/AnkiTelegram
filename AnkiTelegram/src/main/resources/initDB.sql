CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       chat_id INTEGER UNIQUE  NOT NULL,
                       firstname TEXT,
                       lastname TEXT,
                       username TEXT NOT NULL
);
CREATE TABLE deck(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) not null,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE
);
CREATE TABLE flashcard (
    id SERIAL PRIMARY KEY,
    question TEXT NOT NULL,
    answer TEXT NOT NULL,
    interval_days INTEGER DEFAULT 1,
    next_review_date DATE,
    deck_id INTEGER REFERENCES deck(id) ON DELETE CASCADE
);

drop table deck cascade ;
drop table flashcard cascade;
drop table users cascade;