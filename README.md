# Challenge-Spring-Boot
Challenge OracleOne Literatura Spring-Boot

# Book Catalog API

A Java-based application to search, save, and manage books and authors using Spring Boot, JPA, and a relational database.

## Features

- Fetch books data from an external API.
- Save books and authors in a database with proper relationships.
- Query books by language, title, or author.
- Display download statistics for books downloads.
- Store books and authors in a PostgreSQL database.
- List and filter stored books and authors.
- Built with Spring Boot, Hibernate, and JPA.

## Technologies Used

- **Java**: Core language.
- **Spring Boot**: Framework for application setup and development.
- **JPA**: ORM for database interaction.
- **H2 / MySQL**: Relational database for persistence.
- **Jackson**: JSON parsing and mapping.
- **JPQL**: Queries for complex database operations.

## Prerequisites

- Java 17+
- Maven 3.8+
- A relational database (e.g., H2, MySQL)

## API Usage

The project uses the [Project Gutenberg web API](https://gutendex.com/) to retrieve JSON ebook metadata. 

Example API URL:

\`\`\`
https://gutendex.com/books/?search=Moby+dick
\`\`\`

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/book-catalog-api.git
   cd book-catalog-api

2. Open the project in IntelliJ IDEA:

- Click on File > Open.
- Select the book-catalog-api folder.
- IntelliJ will automatically detect it as a Maven project and import the dependencies.

3. Configure the application:

- Navigate to src/main/resources/application.properties to set up your database configurations.

4. Build the project:

- Open the Maven tool window (View > Tool Windows > Maven).
- Click on Lifecycle > clean and then install to build the project.

5. Run the application:

- Open the ScreenmatchApplicationCLI class.
- Click the green play icon next to the main method to run the application.

## Autor

**Andrés Echeverría**  
Fordragon Dev Company.