## Introduction

Event Spotlight is an online platform for hosting events of various categories, allowing users to create, view and find interesting events.
The project is designed to simplify the process of management and participation in events.

## Features

- **Event Creation**: Users can create new events and add detailed information.
- **Categorization**: Events are divided into various categories to facilitate easy search.
- **Dropbox Integration**: Capability to add materials and images to events using Dropbox integration.
- **Search and Filtering**: Quick search and filtering options for events by category and date.

## Technologies Used

- **Java 11**: The core programming language.
- **Spring Boot**: For building and deploying the web application.
- **Spring Data JPA**: To simplify database interactions.
- **Spring Security**: For securing the application and managing user authentication.
- **Swagger**: For API documentation and testing.
- **Docker Compose**: For running the application in Docker.
- **TestContainers**: Container with database used for testing.
- **JUnit**: For writing unit tests.
- **MockMvc**: For testing controllers in Spring.
- **Maven**: For dependency management and project build.
- **Dropbox API**: Integration for file handling

## Architecture

The project follows a multi-layered architecture:

- **Controllers**: Handle HTTP requests and responses.
- **Services**: Contain business logic for data processing and interaction with the database.
- **Repositories**: Used for database access through Spring Data JPA.

## Functionality

The application consists of several controllers that handle:

- User authentication and registration.
- CRUD operations for events and other models.
- Search functionality for finding events category, date, online status, city.
- My and Favorite events management: adding, updating, or removing events.
- Save personal details for example: favorite, myEvents.

## Database Schema

<p>
  <img src="/DB Structure EventSpotlight.png" alt="database_schema"/>
</p>


## Setup Instructions

1. Clone the repository:

    ```bash
    git clone https://github.com/fx-july12-team86/event-spotlight-back
    ```

2. Launch the app on your local device or via Docker:
    - Change the `.env` file to your parameters (e.g., database settings, ports).
    - Run the application with the command:
      ```bash
      docker-compose up
      ```

3. Open Swagger UI using your browser to check functionality:
    - Access it at `http://localhost:8080/swagger-ui/`.

<p align="center">
  <img src="/Swagger.png" alt="swagger_png"/>
</p>


## Postman Setup

[![Run in Postman](https://run.pstmn.io/button.svg)](https://grey-trinity-542243.postman.co/workspace/Event-Spotlight-public-workspac~17958c38-f32f-48b4-ade5-f398793c9029/collection/33969486-91a2b3e8-ee63-47b5-aab2-2b788ac25621?action=share&creator=33969486&active-environment=33969486-bc3d9e9a-a458-4ab9-80e0-18b81e057fa5)

## Contributing

If you would like to contribute to this project:
1. Fork the repository.
2. Create a new branch for your feature or fix.
3. Make your changes and commit them.
4. Submit a pull request.

## Contact

For questions or suggestions

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?logo=linkedin&logoColor=white&style=flat-square)](https://www.linkedin.com/in/yevhen-shumeiko-5a153b26a/)