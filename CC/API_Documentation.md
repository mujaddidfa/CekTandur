# Planticure API
Backend API for CekTandur app.

## Installation
**1. Install using NPM**
```javascript
npm install
```
**How to run**
```javascript
npm run start
```
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
        "userId": "946f1a5fbd2542b5",
        "name": "bangkit",
        "email": "bangkit123@gmail.com",
        "insertedAt": "2024-12-10T15:30:32.302Z",
        "updatedAt": "2024-12-10T15:30:32.302Z"
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
- **Response**: 
- Succes (200 OK):
  ```json
  {
      "status": 200,
      "message": "User logged in successfully",
      "data": {
          "userId": "946f1a5fbd2542b5",
          "name": "bangkit",
          "email": "bangkit123@gmail.com"
      }
  }

### POST `/api/auth/logout`
- **Description**: Logs out a user and invalidates the authentication token.
- **Request Body**:
   ```json
  {
    "email":"munaalin12@gmail.com"
  }
- **Response**:
- Success (200 OK):
  ```json
  {
    "status": 200,
    "message": "User logged out successfully",
    "data": {
        "name": "sayaalin",
        "email": "munaalin12@gmail.com"
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

## Plant Management
### GET `/api/plant`
- **Description**: Fetches a list of all plants.
- **request**:
- none
- **Response**: Returns a list of all plants.
- example:
  ```json
  {
      "error": false,
      "message": "Plant data fetched successfully",
      "plant": {
          "idPlant": 25,
          "className": "Class25",
          "disease_name": "Tanaman Ini Sehat",
          "description": "Sejauh ini Tanaman anda terlihat sehat, terus rawat dengan baik dan berikan pupuk dan perlindungan lainnya secara teratur.",
          "causes": [],
          "treatments": [],
          "alternative_products": []
      }
    }
 - etc.

### GET `/api/plant/:idPlant`
- **Description**: Fetches a list of plants based on a specific class.
- **request**:
- none
- **Response**: Returns a list of plants based on the class.
- **Example**: `/api/plant/idPlant`
  ```json
    {
      "error": false,
      "message": "Plant data fetched successfully",
      "plant": {
          "idPlant": 25,
          "className": "Class25",
          "disease_name": "Tanaman Ini Sehat",
          "description": "Sejauh ini Tanaman anda terlihat sehat, terus rawat dengan baik dan berikan pupuk dan perlindungan lainnya secara teratur.",
          "causes": [],
          "treatments": [],
          "alternative_products": []
      }
    }

### POST `/api/users/history`
- **Description**: Logs plant history.

- **Request Body**:
- multipart/form-data

### Field 
- userId
- className
- diseaseName
- confidence
- plantImage


Required for image upload
- **Response**:

- Success (201 Created):
  ```json
  {
    "status": 201,
    "message": "Plant history saved successfully",
    "data": {
      "userId": "3c910222bee844d5",
      "className": "Tomato",
      "diseaseName": "Late Blight",
      "description": "Fungal disease affecting tomatoes.",
      "confidence": 98,
      "causes": "Caused by a pathogen",
      "treatments": "Use fungicides",
      "alternative_products": ["Fungicide A", "Fungicide B"],
      "alternative_products_links": [
        "http://example.com/productA",
        "http://example.com/productB"
      ],
      "imageUrl": "https://storage.googleapis.com/bucket-name/image.jpg",
      "timestamp": "12/13/2024, 10:00:00 AM"
    }
  }
  
- Error (400 Bad Request):
  ```json
    {
    "status": 400,
    "message": "Missing required fields",
    "error": {
      "details": "Please provide userId and plantImage."
    }
  }

- Error Response (500 Internal Server Error):
  ```json
    {
    "status": 500,
    "message": "Failed to save plant history",
    "error": {
      "details": "Error message"
    }
  }

### GET `/api/plant/history/:userId`
- **Description**: Fetches a list of plant history for a specific user.
- **request**:
- none
- **Response**: Returns a list of plant history for the user.

-Succes (200 OK)
  ```json
    {
        "status": 200,
        "message": "Plant history retrieved successfully",
        "data": [
            {
                "userId": "5b26843f89e54bf8",
                "className": "Class1",
                "plantName": "Penyakit Bercak Daun Isariopsis",
                "analysisResult": "Disease",
                "confidence": "98",
                "timestamp": "2024-12-10T15:46:02.377Z"
            }
        ]
    }

- Error(404):
  ```json
    {
        "status": 404,
        "message": "No plant history found for this user"
    }

- Error Response (500 Internal Server Error):
  ```json
  {
  "status": 500,
  "message": "Failed to retrieve plant histories",
  "error": {
    "details": "Error message"
  }
}
