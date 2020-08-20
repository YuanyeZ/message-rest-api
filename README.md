# Rest API for Messages

### Introduction
This project build REST APIs for handling messages.  
The service will listen port 3000. 
It requires docker on the host to be able to run it locally.
The project has been deployed to GCP with public IP address: 35.203.6.31.
#### build the project
./gradlew clean && ./gradlew assemble 
#### test the project
./gradlew test 
#### build and run the project in docker
./script/build.sh
#### deploy and run the project in docker 
- start service: ./script/run.sh
- stop service: ./script/stop.sh

### Architecture
The project uses Spring framework to handle the HTTP requests:
##### 1. This project uses MVC pattern for API design。
- The REST requests (CRUD) will be processed by handlers in MessageController.java.
- The MessageRepository.java is the persistence layer that will help to store the objects to database. 
##### 2. MongoDB is used in the project for flexible storing the Message with highly availability.
- SequenceGeneratorService.java is used to generate unique message id without exposing the primary 
key of database.
- Version is used to avoid multi-thread conflicts
##### 3. This project provides two strategies to validate the palindrome。
- Restrict: it validates all the characters with origin format
- Alphabet: it validates only the alphabet and digit in the message with case-insensitive
- The mode could be selected by sending the 'restrictMode' in the payload

### REST APIs
#### GET /api/v1/messages?page={page}&size={size}
- parameters: 
  - page: zero-based page index. Default is 0.
  - size: the size of the page to be returned. Default is 5.
- return all the messages in the system
#### GET /api/v1/messages/{messageId}
- parameters: 
  - messageId: id of the message we would like to get.
- return the message with specific message id
- MessageNotFoundException is thrown if the message not found
#### POST /api/v1/message
- parameters: 
  - request body: message object (JSON) we would like to create. 
  In the message, "content" is the necessary field. For example, 
  { "content": "hello world" } 
- return the message created
#### POST /api/v1/messages
- parameters: 
  - request body: list of message objects (JSON) we would like to create. 
  In the message, "content" is the necessary field. For example, 
  [{ "content": "first msg" }，{ "content": "second msg" }] 
- return the list of messages created
#### PUT /api/v1/messages/{messageId}
- parameters: 
  - messageId: id of the message we would like to update
  - request body: message object (JSON) we would like to update.
   For example, sending { "content": "new message" } to 
   /api/v1/messages/1 will update the message with message id 1
- return the updated message
- MessageNotFoundException is thrown if the message not found
#### DELETE /api/v1/messages/{messageId}
- parameters: 
  - messageId: id of the message we would like to delete
- MessageNotFoundException is thrown if the message not found

### Database schema
#### Message
-   id: the unique id generated by database
-   messageId: the unique id for each message for identification
-   content: the content of the message
-   isRestrictMode: whether to use restrict mode to judge palindrome
-   isPalindrome: whether to the message is palindrome
-   createdTime: timestamp to create the message
-   lastUpdate: timestamp to update the message
-   version: the field for multi-version concurrency control
