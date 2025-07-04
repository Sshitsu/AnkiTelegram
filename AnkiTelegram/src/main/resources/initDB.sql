CREATE TABLE users (
                       chat_id INTEGER UNIQUE  NOT NULL,
                       username TEXT NOT NULL,
                       temp_deck_name VARCHAR(255),
                       state VARCHAR(255)
);
CREATE TABLE deck (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      user_chat_id INTEGER NOT NULL,
                      CONSTRAINT fk_deck_user_chat
                          FOREIGN KEY (user_chat_id)
                              REFERENCES users(chat_id)
                              ON DELETE CASCADE
);
CREATE TABLE flashcard (
    id SERIAL PRIMARY KEY,
    question TEXT NOT NULL,
    answer TEXT NOT NULL,
    interval_days INTEGER DEFAULT 1,
    next_review_date DATE,
    deck_id INTEGER REFERENCES deck(id) ON DELETE CASCADE
);



CREATE TABLE deck(
                     id SERIAL PRIMARY KEY,
                     name VARCHAR(255) not null,
                     description varchar(255),
                     user_chat_id INTEGER REFERENCES users(chat_id) ON DELETE CASCADE
);
drop table deck cascade;
drop table flashcard cascade;
drop table users cascade;

