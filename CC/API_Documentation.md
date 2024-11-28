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
- **Response**
- Success (200 OK):
  ```json
  {
    "status": 200,
    "message": "User logged in successfully"
  }

- Error (400 Bad Request):
  ```json
  {
    "status": 400,
    "message": "Invalid credentials",
    "error": {
    "details": "Authentication failed. User not found or incorrect password."
    }
  }

- Error (500 Internal Server Error):
  ```json
  {
    "status": 500,
    "message": "Internal server error",
    "error": {
    "details": "Error message here"
    }
  }

### POST `/api/auth/logout`
- **Description**: 
Logs out a user and invalidates the authentication token
- **Request Body**:
  ```json
  {
    "email": "string"
  }

- **Response**
- Success (200 OK):
  ```json
  {
    "status": 200,
    "message": "User logged out successfully"
  }
  
- Error (400 Bad Request):
```json
  {
    "status": 400,
    "message": "User not found",
    "error": {
    "details": "Logout failed because the user does not exist."
    }
  }
  
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
