# CTO Service API

CTO Service API — это микросервис для управления заявками на обслуживание автомобилей. В проекте используются Spring Boot, PostgreSQL, Kafka, Docker и Liquibase.

## Технологии

- Java 21 (Amazon Corretto)
- Spring Boot
- PostgreSQL
- Apache Kafka
- Liquibase
- Docker
- Docker Compose

## Установка проекта

### Клонирование репозитория
### (Переход в корневую папку с docker-compose and dockerfile) cd cto-service

### Сборка проекта
./gradlew build -x test
### Запуск проекта
docker-compose up --build -d

# Доступные сервисы после запуска
| Сервис      | Адрес подключения                              |
| ----------- | ---------------------------------------------- |
| Backend API | [http://localhost:8080](http://localhost:8080) |
| PostgreSQL  | jdbc\:postgresql://localhost:3432              |
| Kafka       | localhost:9092                                 |
| Zookeeper   | localhost:2181                                 |

# Тестовые пользователи
| Email                                                           | Роль           | Пароль  |
| --------------------------------------------------------------- | -------------- | ------- |
| [alice.user@example.com](mailto:alice.user@example.com)         | ROLE\_USER     | string |
| [bob.user@example.com](mailto:bob.user@example.com)             | ROLE\_USER     | string |
| [carol.employee@example.com](mailto:carol.employee@example.com) | ROLE\_EMPLOYEE | string |
| [david.employee@example.com](mailto:david.employee@example.com) | ROLE\_EMPLOYEE | string |

# Тестовые автомобили
| Владелец                                                | Автомобиль   |
| ------------------------------------------------------- | ------------ |
| [alice.user@example.com](mailto:alice.user@example.com) | Toyota Camry |
| [bob.user@example.com](mailto:bob.user@example.com)     | Honda Civic  |

# REST API (остальные указаны в swagger или в постман коллекции)
| Метод | Endpoint                             | Описание                                |
| ----- | ------------------------------------ | --------------------------------------- |
| GET   | /api/v1/service-requests/user        | Получить заявки пользователя            |
| POST  | /api/v1/service-requests             | Создать новую заявку                    |
| POST | /api/v1/service-requests/accept/{id} | Принять заявку (только для сотрудников) |

