# 🏆 YuChess

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![WebSocket](https://img.shields.io/badge/WebSocket-Enabled-blue.svg)](https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API)

A modern, real-time chess web server built with Spring Boot and WebSockets, featuring microservices architecture for scalable online chess gameplay.

## 🚀 Features

- **Real-time Gameplay** - WebSocket-based live chess matches
- **User Management** - JWT-based authentication and user profiles
- **Smart Matchmaking** - ELO-based player matching system
- **Microservices Architecture** - Modular, scalable backend services
- **RESTful APIs** - Clean API design for frontend integration
- **Security** - OAuth2 resource server with Spring Security

## 🏗️ Architecture

YuChess follows a microservices architecture with three main services:

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│     Users       │    │   Matchmaking   │    │     Games       │
│   Service       │    │    Service      │    │    Service      │
│                 │    │                 │    │                 │
│ • Authentication│    │ • Player Queue  │    │ • Game Logic    │
│ • User Profiles │    │ • ELO Matching  │    │ • Chess Engine  │
│ • JWT Tokens    │    │ • WebSocket Hub │    │ • Move Validation│
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### Services Overview

| Service | Port | Description | Technologies |
|---------|------|-------------|--------------|
| **Users** | 8080 | User authentication, profiles, JWT management | Spring Security, JPA, PostgreSQL/MySQL |
| **Matchmaking** | 8081 | Player queuing, ELO-based matching | WebSocket |
| **Games** | 8082 | Chess game logic, move validation, game state | WebSocket, Chess Engine |

## 🛠️ Tech Stack

- **Backend**: Java 17, Spring Boot 3.5.4
- **Security**: Spring Security, JWT, OAuth2
- **Real-time**: WebSocket, STOMP
- **Database**: PostgreSQL
- **Build Tool**: Maven
- **Additional**: Lombok, MapStruct, SpringDoc OpenAPI

  
## 📁 Project Structure

```
yuchess/
├── users/                          # Users login / register Service
│   ├── src/main/java/com/yuchess/users/
│   │   ├── business/
│   │   │   ├── config/             # Security & Bean configuration
│   │   │   ├── entity/             # JPA entities
│   │   │   ├── enums/              # Enumerations
│   │   │   ├── impl/               # Service implementations
│   │   │   ├── mapper/             # MapStruct mappers
│   │   │   ├── repository/         # JPA repositories
│   │   │   ├── security/           # JWT filters
│   │   │   └── util/               # Utility classes
│   │   └── server/
│   │       ├── controller/         # REST controllers
│   │       ├── dto/                # Data Transfer Objects
│   │       └── response/           # Response models
│   └── pom.xml
├── matchmaking/                    # Matchmaking Service
│   ├── src/main/java/com/yuchess/matchmaking/
│   │   ├── business/
│   │   │   ├── config/             # WebSocket configuration
│   │   │   └── enums/              # Queue & ELO enums
│   │   └── server/
│   │       ├── controller/         # Websocket controllers
│   │       └── dto/                # Data Transfer Objects
│   └── pom.xml
├── games/                          # Games Service
│   ├── src/main/java/com/yuchess/games/
│   │   ├── business/
│   │   │   ├── config/             # WebSocket configuration
│   │   │   ├── engine/             # Chess game engine
│   │   │   └── enums/              # Game status & piece enums
│   │   └── server/
│   │       ├── controller/         # Game controllers
│   │       └── message/            # WebSocket messages
│   └── pom.xml
├── .gitignore
└── README.md
```

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL database

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/yuchess.git
cd yuchess
```

### 2. Database Setup
Configure your database connections in each service's `application.properties`:

**Users Service** (`users/src/main/resources/application.properties`):
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/yuchess_users
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Build All Services
```bash
# Build Users Service
cd users
./mvnw clean install
cd ..

# Build Matchmaking Service
cd matchmaking
./mvnw clean install
cd ..

# Build Games Service
cd games
./mvnw clean install
cd ..
```

### 4. Run Services
Start each service in separate terminals:

```bash
# Terminal 1 - Users Service
cd users
./mvnw spring-boot:run

# Terminal 2 - Matchmaking Service
cd matchmaking
./mvnw spring-boot:run

# Terminal 3 - Games Service
cd games
./mvnw spring-boot:run
```

## 📡 API Endpoints

### Users Service (Port 8080)
- `POST /api/users/register` - User registration
- `POST /api/users/login` - User authentication
- `GET /api/users/profile` - Get user profile
- `PUT /api/users/profile` - Update user profile

### Matchmaking Service (Port 8081)
- `POST /api/matchmaking/join` - Join matchmaking queue
- `DELETE /api/matchmaking/leave` - Leave matchmaking queue
- `GET /api/matchmaking/status` - Get queue status

### Games Service (Port 8082)
- `POST /api/games/create` - Create new game
- `POST /api/games/{gameId}/join` - Join existing game
- `POST /api/games/{gameId}/move` - Make a move
- `GET /api/games/{gameId}` - Get game state

## 🔌 WebSocket Endpoints

### Matchmaking WebSocket
```
ws://localhost:8081/matchmaking
```
- Subscribe to `/topic/match-found` for match notifications
- Send to `/app/join-queue` to join matchmaking

### Game WebSocket
```
ws://localhost:8082/game/{gameId}
```
- Subscribe to `/topic/game/{gameId}` for game updates
- Send to `/app/move` to make moves

## 🎮 Game Flow

1. **User Registration/Login** → Users Service
2. **Join Matchmaking Queue** → Matchmaking Service
3. **Match Found** → WebSocket notification
4. **Game Creation** → Games Service
5. **Real-time Gameplay** → WebSocket communication
6. **Game Completion** → Update user statistics

## 📚 Documentation

- **API Documentation**: Available at `http://localhost:8080/swagger-ui.html` (Users Service)
- **Chess Rules**: Standard FIDE chess rules implemented
- **WebSocket Protocol**: STOMP over WebSocket for real-time communication

---
