# AnkiTelegram Bot  

AnkiTelegram is a Telegram bot that implements the functionality of the [Anki](https://apps.ankiweb.net/) app for memorizing information using flashcards based on spaced repetition.
##  Features

-  Create and delete decks
-  Add flashcards to decks
-  Review flashcards using spaced repetition
-  Handle success/failure and adjust the next review time accordingly
-  Persist data with PostgreSQL
-  Telegram bot interface

##  Tech Stack
- Java 17+
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Telegram Bots Java API
- Maven

##  Setup and Run

1. **Clone the repository**
git clone https://github.com/Sshitsu/AnkiTelegram.git
cd AnkiTelegram
 

2. **Set environment variables or use `.env`**

Add the following to your `application.properties` or set them in your environment:

 properties:
  BOT_USERNAME=your_bot_username
  BOT_TOKEN=your_bot_token

  SPRING_DATASOURCE_URLyour_ds_url
  SPRING_DATASOURCE_USERNAME=your_db_username
  SPRING_DATASOURCE_PASSWORD=your_db_password
 

3. **Configure the database**

Make sure PostgreSQL is running and create a database named `anki_db`.

4. **Build and run**

bash:
./mvnw spring-boot:run
 

The bot will start listening for Telegram updates.

##  Project Structure
 
src/
├── bot/                    - Telegram bot logic
├── entity/                 - JPA entities: Deck and FlashCard
├── models/                 - DTOs for user-facing models
├── repository/             - Spring Data repositories
├── service/                - Business logic: reviewing, adding cards, etc.
└── exeception/             - Custom exception classes
 

## Spaced Repetition Algorithm

* On successful review: interval doubles (`interval *= 2`)
* On failure: interval resets to 1 day
* Only cards with `nextReviewedAt <= today` are shown for review

## Contributing

Pull requests are welcome! If you have suggestions or ideas, feel free to open an issue.


### Contact
th
Developed by [@Sshitsu](https://github.com/Sshitsu).
For questions or contributions, open an issue or PR.

 
