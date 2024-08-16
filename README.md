# Spring Boot Sentiment Analyzer

## Overview

This project is an approach on how to invoke the sentiment analysis models using Spring Boot. It's built using Spring Boot and provides [some features or functionalities your project offers].

## Prerequisites

Before you begin, ensure you have the following installed:

- Java 11 or higher
- Maven 3.6.0 or higher
- Git

## Setup Instructions

### 1. Clone the Repository

First, clone the repository to your local machine:

```bash
git clone <repository-url>
cd sentimentAnalyzer
```

### 2. Create `application.properties` File

Create a new file in the `src/main/resources` directory named `application.properties` and add the following:

```properties
# Server Configuration
server.port=8080
spring.application.name=sentimentAnalyzer
# Example configuration
api.token=your_api_token_here
```

Ensure that you replace the placeholder values with your actual configuration details.

### 3. Build the Project

Next, build the project using Maven:

```bash
mvn clean install
```

### 4. Run the Application

Finally, run the application using the following command:

```bash
mvn spring-boot:run
```

The application will be accessible at `http://localhost:8080`.

## API Endpoints

The following endpoints are available:

1. `POST /api/v1/sentiment/stars`:
   This endpoint analyzes the sentiment of the provided text and returns a star rating.

    **Request Body:**

    ```json
    {
        "text": "I love this product!"
    }
    ```

    **Response:**

    ```json
    {
        "text": "I love this product!",
        "sentimentJson": "[[{\"label\":\"5 stars\",\"score\":0.9135048389434814},{\"label\":\"4 stars\",\"score\":0.07795187830924988},{\"label\":\"3 stars\",\"score\":0.005851863417774439},{\"label\":\"1 star\",\"score\":0.001568933017551899},{\"label\":\"2 stars\",\"score\":0.0011225317139178514}]]",
        "finalSentiment": 5
    }
    ```
   
2. `POST /api/v1/sentiment/core`: This endpoint analyzes the sentiment of the provided text and returns sentiment analysis results using existing Java libraries
    
    **Request Body:**

    ```json
    {
        "text": "I love this product!"
    }
    ```

    **Response:**

    ```json
    {
        "sentimentMap": {
        "I love this product!": 3
    },
    "finalSentiment": 4,
    "finalSentimentName": "Positive"
    }
    ```

3. `POST /api/v1/sentiment/binary`: This endpoint analyzes the sentiment of the provided text and returns a binary sentiment (positive or negative).

   **Request Body:**

    ```json
    {
        "text": "I love this product!"
    }
    ```

   **Response:**

    ```json
    {
    "text": "I love this product!",
    "sentiment": "[[{\"label\":\"POSITIVE\",\"score\":0.9998855590820312},{\"label\":\"NEGATIVE\",\"score\":0.00011442826507845894}]]",
    "finalSentiment": "POSITIVE"
   }
    ```