CREATE TABLE deck(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) not null
);
CREATE TABLE flashcard (
                           id SERIAL PRIMARY KEY,
                           question TEXT NOT NULL,
                           answer TEXT NOT NULL,
                           interval_days INTEGER DEFAULT 1,
                           next_review_date DATE,
                           deck_id INTEGER REFERENCES deck(id) ON DELETE CASCADE
);