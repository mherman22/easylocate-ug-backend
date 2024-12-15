# EasyLocate UG Backend

A Spring Boot backend application for managing business locations and details in Uganda.

## 🚀 Features

- CRUD operations for businesses
- Image upload functionality
- Location management
- Cross-origin resource sharing (CORS) enabled
- File size validation
- Supported image formats (JPG, JPEG, PNG, GIF)

## 🛠️ Tech Stack

- Java 8
- Spring Boot 2.7.18
- Spring Data JPA
- H2 Database (in-memory)
- Project Lombok
- Commons IO

## 📋 Prerequisites

- JDK 8
- Maven 3.x
- At least 10MB of free disk space for image uploads

## 🔧 Configuration

The application can be configured through `application.properties`: 

### Maximum file upload sizes
- `spring.servlet.multipart.max-file-size=10MB`
- `spring.servlet.multipart.max-request-size=10MB`

### Upload directory
`file.upload-dir=uploads`

## 🏃‍♂️ Running the Application
1. Clone the repository: 
```bash
git clone https://github.com/yourusername/easylocate-ug-backend.git
```
2. Navigate to the project directory:
```bash
cd easylocate-ug-backend
```
3. Create the uploads directory:
```bash
mkdir uploads
```
4. Build the Project:
```bash
mvn clean install
```
5. Run the application:
```bash
mvn spring-boot:run
```
The application will start on `http://localhost:8022`

## 📚 API Documentation

### Business Endpoints

#### Get All Businesses
```http
GET /api/businesses
```
#### Get Business by ID
```http
GET /api/businesses/{id}
```
#### Create New Business
```http
POST /api/businesses
```
**Content-Type**: multipart/form-data
##### Parameters:
- image: (file) Business image
- business: (JSON) Business details

## 🔒 Security Considerations

- The H2 console is enabled and accessible at `/h2-console` (disable in production)
- CORS is currently configured to accept requests from all origins (*)
- File upload validation is implemented for size and type
- Default H2 database credentials (change in production):
  - Username: sa
  - Password: (empty)

## 🚨 Known Issues

- H2 database is in-memory and will reset on application restart
- Images are stored in local filesystem (consider using cloud storage in production)

## 📝 TODO

- [ ] Add user authentication
- [ ] Implement cloud storage for images
- [ ] Add pagination for business listings
- [ ] Implement search functionality
- [ ] Add API documentation using Swagger/OpenAPI

## 👥 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details

## 🤝 Contact

- [@m_herman22](https://x.com/m_herman22)
- Project Link: [https://github.com/mherman22/easylocate-ug-backend](https://github.com/yourusername/easylocate-ug-backend)