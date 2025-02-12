# Quiz"R"Us Architecture

## Table of Contents
- [Quiz"R"Us Architecture](#quizrus-architecture)
  - [Table of Contents](#table-of-contents)
  - [Application](#application)
  - [Domain Specific Objects](#domain-specific-objects)
    - [User](#user)
    - [Quiz](#quiz)
    - [Question](#question)
    - [Answer](#answer)
  - [Presentation Layer](#presentation-layer)
      - [QuizSelectionActivity](#quizselectionactivity)
      - [QuizRecycleViewAdapter](#quizrecycleviewadapter)
      - [MCQuestionAcitivity](#mcquestionacitivity)
  - [Business Layer](#business-layer)
      - [AccessUsers](#accessusers)
      - [AccessQuizzes](#accessquizzes)
      - [AccessQuestions](#accessquestions)
      - [AccessAnswers](#accessanswers)
    - [Random](#random)
- [Persistence Layer](#persistence-layer)
      - [UserPersistence](#userpersistence)
        - [UserPersistenceStub](#userpersistencestub)
      - [QuizPersistence](#quizpersistence)
        - [QuizPersistenceStub](#quizpersistencestub)
      - [QuestionPersistence](#questionpersistence)
        - [QuestionPersistenceStub](#questionpersistencestub)
      - [AnswerPersistence](#answerpersistence)
        - [AnswerPersistenceStub](#answerpersistencestub)

## Application
The application directory holds the classes that provide access to the perisstence classes. In iteration 1, `Services.java` provides clean access to `UserPersistence`, `QuizPersistence`, `QuestionPersistence`, and `AnswerPersistence` classes.

**Package**: `comp3350.quizrus.application;`

## Domain Specific Objects
This section defines the core objects of the Quiz"R"Us system. They are an integral part of all 3 layers.

**Package**: `comp3350.quizrus.application;`

### User
The `User` represents an individual interacting with the system e.g., working with a quiz. A quiz must be associated with a user.

### Quiz
The `Quiz` object represents a collection of questions grouped together for a specific purpose.

### Question
Each `Question` belongs to a quiz and consists of a prompt and multiple possible answers.

### Answer
An `Answer` represents a response to a question. It can be correct or incorrect, depending on the question type (multiple choice, true/false, etc.).

![ER-Diagram](ER-Digram.png)

## Presentation Layer
This section is resposible for all of the UI.

**Package**: `comp3350.quizrus.presentation;`

#### QuizSelectionActivity
The Main page of the app, where it shows a list of quizzes, and user can select which quiz they want to play 

#### QuizRecycleViewAdapter
A recycle view adapter is responsible for setting up the quiz items in the QuizSelectionActivity UI page

#### MCQuestionAcitivity
A multiple choice questions UI page that opens up when the user select a quiz in the quiz selection page UI, Responsible for showingwthe questions and it's available answeisafter user submiting the answer. re su#has selected a q# z in the quiz selection page UI.

## Business Layer
This section is responsible for communication between the UI and database layers.

**Package**: `comp3350.quizrus.business;`

#### AccessUsers
This is used by the user interface to get a list of all the users in the database.

#### AccessQuizzes
This is used by the user interface to get a list of all the quizzes in the database.

#### AccessQuestions
This is used by the user interface to get a list of all the questions for a quiz in the database.

#### AccessAnswers
This is used by the user interface to get a list of all the answers for a question in the database. There is also a method for findiing the position of the correct answer.

### Random
This includes an algorithm which will be used to randomize the order of both the questions and answers.

# Persistence Layer
The `Persistence Layer` is responsible for storing and retrieving data. This layer ensures data integrity and optimal performance through efficient database or stub interactions.

**Package**: `comp3350.quizrus.persistence;`  

#### UserPersistence
Handles user-related data operations, such as registration, login, and profile management.

##### UserPersistenceStub
A mock implementation for testing `User` operations for.

#### QuizPersistence
Manages quiz-related storage.

##### QuizPersistenceStub
A mock implementation for testing `Quiz` operations.

#### QuestionPersistence
Manages question-related storage.

##### QuestionPersistenceStub
A mock implementation for testing `Queion` operations.

#### AnswerPersistence
Manages answer-related storage.

##### AnswerPersistenceStub
A mock implementation for testing `Answer` operations.
