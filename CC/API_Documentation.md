




---

## Authentication
### POST `/api/auth/register`
- **Description**: Registers a new user.
- **Request Body**:
  ```json
  {
    "name": "string",
    "email": "string",
    "password": "string"
  }
- **Response**: 
- Success (201 Created)
  ```json
  {
    "status": 201,
    "message": "User registered successfully",
    "data": {
    "email": "newuser@example.com"
    }
  }

- Error (400 Bad Request)
  ```json
  {
    "status": 400,
    "message": "Invalid input",
    "error": {
    "details": "Email and password are required."
    }
  }

- Error (500 Internal Server Error)
  ```json
  {
    "status": 500,
  "message": "Internal server error",
  "error": {
    "details": "Error message here"
    }
  }

### POST `/api/auth/login`
- **Description**: Logs in a user and provides an authentication token.
- **Request Body**:
  ```json
  {
    "email": "string",
    "password": "string"
  }
- **Response**: Returns a token for user authentication.

## User Management
### GET `/api/users/:userId`
- **Description**: Fetches information of a specific user.
- **Response**: Returns user data based on userId.

### PUT `/api/users/:userId`
- **Description**: Updates user information.
- **Request Body**:
  ```json
  {
    "email": "string",
    "password": "string"
  }
- **Response**: Returns updated user data.

### DELETE `/api/users/:userId`
- **Description**: Deletes a user by userId.
- **Response**: Returns confirmation of user deletion.
