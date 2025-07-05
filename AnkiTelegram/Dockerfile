# Первая стадия: 'builder'
# Используем образ с JDK для сборки вашего Maven проекта
FROM eclipse-temurin:17-jdk-alpine AS builder

# Устанавливаем рабочую директорию внутри контейнера для сборки
WORKDIR /opt/app

# Копируем файлы Maven Wrapper и pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Загружаем зависимости Maven в автономном режиме для ускорения последующих сборок
RUN ./mvnw dependency:go-offline

# Копируем исходный код вашего приложения
COPY ./src ./src

# Компилируем и упаковываем приложение. Это создаст JAR-файл в target/
RUN ./mvnw clean install

# Вторая стадия: 'runner'
# Используем более легкий образ с JRE для запуска готового приложения
FROM eclipse-temurin:17-jre-alpine

# Устанавливаем рабочую директорию внутри контейнера для запуска
WORKDIR /opt/app

# Копируем ТОЛЬКО исполняемый JAR-файл из стадии 'builder'
# Убедитесь, что имя файла (AnkiTelegram-0.0.1-SNAPSHOT.jar) точно соответствует вашему проекту.
# Мы переименовываем его в 'app.jar' для удобства и краткости.
COPY --from=builder /opt/app/target/AnkiTelegram-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт 8080, который будет использоваться вашим Java-приложением
EXPOSE 8080

# Определяем команду, которая будет выполняться при запуске контейнера
# Теперь мы явно указываем имя JAR-файла 'app.jar'
ENTRYPOINT ["java", "-jar", "app.jar"]
