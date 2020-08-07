# Rest API for Messages

### Introduction
This project build REST APIs for handling messages. 
To build and run the project locally, run "./run.sh". It requires docker on the host to be able to run it.

### Architecture
The project uses Spring framework to handle the HTTP requests:
1) The code entry is InterviewApplication.java
2) The REST requests (CRUD) will be processed by handlers in MessageController.java
3) The MessageRepository.java is the persistence layer that will help to store the objects to MongoDB. 
4) MessageService.java provides the functionality to valid whether the message is palindrome or not.
5) SequenceGeneratorService.java is used to generate unique message id without exposing the primary 
key of database.

### REST APIs
#### GET /api/v1/messages
- return all the messages in the system
#### GET /api/v1/messages/{messageId}
- parameter: 
  - messageId: id of the message we would like to get.
- return the message with specific message id
#### POST /api/v1/message
- parameter: 
  - request body: message object (JSON) we would like to create. 
  In the message, "content" is the necessary field. For example, 
  { "content": "hello world" } 
- return the message created
#### PUT /api/v1/messages/{messageId}
- parameter: 
  - messageId: id of the message we would like to update
  - request body: message object (JSON) we would like to update.
   For example, sending { "content": "new message" } to 
   /api/v1/messages/1 will update the message with message id 1
- return the updated message
#### DELETE /api/v1/messages/{messageId}
- parameter: 
  - messageId: id of the message we would like to delete
 
### TODO:
unit test, cloud deployment, jenkins pipeline, auth, etc...
