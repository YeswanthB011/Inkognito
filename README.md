# 🔒 Inkognito Virtual Diary

**Inkognito** is a private, secure virtual diary application built with Java Spring Boot and H2 database. It provides a simple, user-friendly interface for tracking daily thoughts, feelings, and activities across multiple categories.

## ✨ Features

### 🔐 Privacy & Security
- **Private by Design**: Your thoughts and entries are stored locally in a file-based H2 database
- **No External Dependencies**: All data stays on your machine
- **Simple Authentication**: Username-based login system

### 📝 Daily Check-ins
- **Daily Mood Tracking**: Record how you feel each day (auto-expires after 24 hours)
- **Life Situation Reflection**: Share thoughts about your current life situation
- **Automatic Cleanup**: Daily feelings are automatically cleared after 24 hours

### 🗂️ Organized Categories
Track your life across 8 different categories:

1. **📚 Books** - Reading progress, reviews, and favorite quotes
2. **💪 Workout** - Fitness journey, exercises, and achievements
3. **🎓 Learnings** - Skills development and knowledge acquisition
4. **🏫 Academy** - Educational milestones and academic progress
5. **🎮 Video Games** - Gaming experiences and achievements
6. **🎵 Music** - Musical discoveries and concert experiences
7. **🎬 Movies** - Film reviews and favorite scenes
8. **💝 Relationships** - Personal connections and meaningful interactions

### 🎯 User-Friendly Interface
- **Minimal Design**: Clean, modern interface that's easy to navigate
- **Responsive Layout**: Works on desktop, tablet, and mobile devices
- **Category-Specific Forms**: Tailored input fields for each category
- **Visual Feedback**: Star ratings and duration tracking
- **Smart Tips**: Category-specific guidance for better journaling

## 🛠️ Technology Stack

- **Backend**: Java 17, Spring Boot 3.2.0
- **Database**: H2 (File-based)
- **Frontend**: Thymeleaf, HTML5, CSS3
- **Build Tool**: Maven
- **Styling**: Custom CSS with modern glassmorphism design

## 📋 Prerequisites

- **Java 17** or higher
- **Maven 3.6+** (or use the included Maven wrapper)
- **Web Browser** (Chrome, Firefox, Safari, Edge)

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd inkognito-diary
```

### 2. Build the Application
```bash
# Using Maven
mvn clean install

# Or using Maven wrapper (if available)
./mvnw clean install
```

### 3. Run the Application

#### Option A: Using the Startup Script (Recommended)
```bash
# Make the script executable (if not already)
chmod +x start.sh

# Start the application
./start.sh
```

#### Option B: Manual Maven Command
```bash
# Using Maven
mvn spring-boot:run

# Or using Maven wrapper
./mvnw spring-boot:run

# Or run the JAR file directly (after building)
java -jar target/inkognito-diary-0.0.1-SNAPSHOT.jar
```

### 4. Stop the Application
```bash
# Using the stop script
./stop.sh

# Or manually with Ctrl+C in the terminal where the app is running
```

### 5. Access the Application
Open your web browser and navigate to:
```
http://localhost:8080
```

## 📖 Usage Guide

### First Time Setup
1. **Visit the Homepage**: Go to `http://localhost:8080`
2. **Create Account**: Fill in the registration form with:
   - Unique username
   - Professional job (optional)
   - Hobby/Interest (optional)
3. **Login**: Use your username to access your diary

### Daily Check-in
1. **Update Daily Feeling**: Select how you feel today from the dropdown
2. **Life Situation**: Share your thoughts about your current life situation
3. **24-Hour Limit**: You can only update your daily feeling once per day

### Adding Entries
1. **Choose Category**: Click on any category from the dashboard
2. **Fill Entry Form**: 
   - **Title**: Descriptive title for your entry
   - **Content**: Detailed description or notes
   - **Rating**: Optional 1-5 star rating
   - **Duration**: Time spent (for applicable categories)
3. **Submit**: Click "Add Entry" to save

### Managing Entries
- **View Entries**: All entries are displayed in chronological order
- **Delete Entries**: Use the trash icon to remove unwanted entries
- **Recent Overview**: Dashboard shows recent entries from all categories

## 🗃️ Database Information

### H2 Database Console (Development)
- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:file:./data/inkognito_db`
- **Username**: `sa`
- **Password**: (empty)

### Data Storage
- Database files are stored in the `./data/` directory
- Files are created automatically on first run
- Data persists between application restarts

## 📁 Project Structure

```
inkognito-diary/
├── src/
│   ├── main/
│   │   ├── java/com/inkognito/
│   │   │   ├── InkognitoDiaryApplication.java
│   │   │   ├── controller/
│   │   │   │   └── MainController.java
│   │   │   ├── model/
│   │   │   │   ├── User.java
│   │   │   │   └── DiaryEntry.java
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── DiaryEntryRepository.java
│   │   │   └── service/
│   │   │       ├── UserService.java
│   │   │       └── DiaryEntryService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── templates/
│   │           ├── base.html
│   │           ├── index.html
│   │           ├── dashboard.html
│   │           └── category.html
├── data/ (created automatically)
├── pom.xml
└── README.md
```

## ⚙️ Configuration

### Application Properties
Key configurations in `src/main/resources/application.properties`:

```properties
# Server port
server.port=8080

# H2 Database (file-based)
spring.datasource.url=jdbc:h2:file:./data/inkognito_db
spring.datasource.username=sa
spring.datasource.password=

# JPA settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# H2 Console (development)
spring.h2.console.enabled=true
```

### Customization Options
- **Port**: Change `server.port` to run on a different port
- **Database Location**: Modify the database URL to change storage location
- **Logging**: Enable SQL logging by setting `spring.jpa.show-sql=true`

## 🔧 Development

### Running in Development Mode
```bash
# Enable auto-restart on file changes
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=dev"
```

### Building for Production
```bash
# Create executable JAR
mvn clean package -DskipTests

# Run production JAR
java -jar target/inkognito-diary-0.0.1-SNAPSHOT.jar
```

## 🐛 Troubleshooting

### Common Issues

1. **Port Already in Use**
   - Change the port in `application.properties`
   - Or kill the process using port 8080: `lsof -ti:8080 | xargs kill`

2. **Database Connection Issues**
   - Ensure the `./data/` directory is writable
   - Check file permissions
   - Restart the application

3. **Maven Build Failures**
   - Ensure Java 17+ is installed and configured
   - Clear Maven cache: `mvn clean`
   - Update dependencies: `mvn clean install -U`

4. **Template Not Found Errors**
   - Ensure all Thymeleaf templates are in `src/main/resources/templates/`
   - Check template syntax and file extensions

### Logs and Debugging
- Application logs are printed to the console
- Enable debug logging by adding `logging.level.com.inkognito=DEBUG` to `application.properties`
- Check H2 console for database inspection

## 📊 Features Overview

| Feature | Description | Status |
|---------|-------------|--------|
| User Registration | Create new user accounts | ✅ |
| User Login | Username-based authentication | ✅ |
| Daily Mood Tracking | Record daily feelings (24h expiry) | ✅ |
| Category-based Entries | 8 different life categories | ✅ |
| Star Ratings | Rate experiences 1-5 stars | ✅ |
| Duration Tracking | Track time spent on activities | ✅ |
| Entry Management | Add, view, delete entries | ✅ |
| Responsive Design | Mobile-friendly interface | ✅ |
| Data Persistence | File-based H2 database | ✅ |
| Auto-cleanup | Daily feeling expiration | ✅ |

## 🤝 Contributing

This is a personal diary application, but if you'd like to contribute:

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 🙏 Acknowledgments

- **Spring Boot Team** for the excellent framework
- **H2 Database** for the lightweight database solution
- **Thymeleaf** for the templating engine
- **Modern CSS** techniques for the beautiful interface

## 📞 Support

If you encounter any issues or have questions:

1. Check the troubleshooting section above
2. Review the application logs
3. Inspect the H2 database console
4. Create an issue in the repository

---

**Happy Journaling! 📝✨**

Remember: Your thoughts and experiences matter. Take a few minutes each day to reflect and record your journey with Inkognito.