# EduRise - User Registration and Login System

A Java Swing application with a modern UI for user registration and login functionality.

## Features

### ✅ User Registration
- **Persistent Storage**: User data is saved to a local file (`users.txt`) and persists between application sessions
- **Form Validation**: Ensures all fields are filled before registration
- **Duplicate Email Check**: Prevents multiple accounts with the same email address
- **Success Feedback**: Shows confirmation dialog after successful registration

### ✅ User Login
- **Authentication**: Validates user credentials against stored data
- **Modern UI**: Clean, professional interface with smooth animations
- **Error Handling**: Clear error messages for invalid credentials

### ✅ Data Persistence
- **File-based Storage**: User data is stored in `users.txt` file
- **Automatic Loading**: Application loads existing users on startup
- **Data Format**: Each user record contains: `name|email|password`

## How to Run

### Prerequisites
- Java JDK 8 or higher
- Windows operating system (for the provided batch file)

### Running the Application

1. **Using the batch file (Recommended)**:
   ```bash
   build.bat
   ```

2. **Manual compilation and execution**:
   ```bash
   # Create bin directory
   mkdir bin
   
   # Compile with classpath
   javac -cp "src;src/miglayout-4.0.jar;src/TimingFramework-0.55.jar" -d bin src/Main.java src/component/*.java src/model/*.java src/panels/*.java src/swing/*.java src/window/*.java
   
   # Run the application
   java -cp "bin;src/miglayout-4.0.jar;src/TimingFramework-0.55.jar" main.Main
   ```

## File Structure

```
eduriseg10aa/
├── src/
│   ├── Main.java                 # Main application entry point
│   ├── component/
│   │   ├── PanelCover.java          # Cover panel with animations
│   │   └── PanelLoginAndRegister.java # Login/Register interface
│   ├── model/
│   │   ├── UserManager.java         # User data management
│   │   └── MainApp.java             # Main application after login
│   ├── swing/
│   │   ├── Button.java              # Custom button component
│   │   ├── MyTextField.java         # Custom text field
│   │   └── MyPasswordField.java     # Custom password field
│   ├── panels/                      # Application panels
│   ├── window/                      # Additional windows
│   ├── icon/                        # Application icons
│   ├── miglayout-4.0.jar           # Layout manager library
│   └── TimingFramework-0.55.jar    # Animation library
├── build.bat                        # Build and run script
└── README.md                        # This file
```

## User Data Storage

User registration data is stored in `users.txt` in the following format:
```
John Doe|john@example.com|password123
Jane Smith|jane@example.com|securepass456
```

Each line represents one user with fields separated by the `|` character.

## Security Notes

- Passwords are stored in plain text (for demonstration purposes)
- In a production environment, passwords should be hashed
- The `users.txt` file should be protected with appropriate file permissions

## Dependencies

- **MigLayout**: For flexible layout management
- **Timing Framework**: For smooth animations and transitions
- **Java Swing**: For the GUI components 