package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/edurisedb?user=root";
    private static final String USER = "root";
    private static final String PASS = "#mercadofio0811";
    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        // Don't create connection immediately, create it when needed
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Register JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Create new connection
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
                
                // Set auto-commit to false for transaction control
                connection.setAutoCommit(false);
                
                System.out.println("Connected to MySQL database: " + DB_URL);
                System.out.println("Auto-commit disabled for transaction control");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Make sure the JAR is in the classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to connect to MySQL database: " + DB_URL);
            e.printStackTrace();
        }
        return connection;
    }

    public boolean isConnectionValid() {
        try {
            return connection != null && !connection.isClosed() && connection.isValid(5);
        } catch (SQLException e) {
            return false;
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Closed MySQL database connection.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing MySQL database connection.");
            e.printStackTrace();
        }
    }

    // Method to close connection only when application is shutting down
    public void shutdown() {
        closeConnection();
    }

    // You might add methods here to check/create tables if needed for new data structures,
    // but for existing tables, it's assumed they are already managed in MySQL Workbench.

    // Check if an application exists for a given StudID
    public boolean applicationExistsForStudID(String studID) {
        String sql = "SELECT COUNT(*) FROM applicantinformation WHERE StudID = ?";
        try (Connection conn = getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studID);
            try (java.sql.ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Check if an AppID already exists
    public boolean appIDExists(String appID) {
        String sql = "SELECT COUNT(*) FROM applicantinformation WHERE AppID = ?";
        try (Connection conn = getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appID);
            try (java.sql.ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Fetch the AppID for a user by their email (from studentinformation and applicantinformation)
    public String getAppIDByEmail(String email) {
        String sql = "SELECT ai.AppID FROM applicantinformation ai JOIN studentinformation si ON ai.StudID = si.StudID WHERE si.StudEmail = ? ORDER BY ai.AppDate DESC LIMIT 1";
        try (Connection conn = getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (java.sql.ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("AppID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public FormData getFormDataByEmail(String email) {
        FormData data = null;
        String sql = """
            SELECT 
                s.*, a.AppID, a.AppDate, a.AppStatus,
                p.ParentID, p.ParentType, p.Name as ParentName, p.Birthdate as ParentBirthdate,
                p.CellNo as ParentCellNo, p.Email as ParentEmail, p.PermanentAdd as ParentPermanentAdd,
                p.CivilStatus as ParentCivilStatus, p.Nationality as ParentCitizenship,
                p.Occupation, p.EducAttainment, p.MonthlyIncome,
                c.ChildID, c.Name as ChildName, c.BirthDate as ChildBirthDate,
                c.Sex as ChildSex, c.Age as ChildAge, c.GradeYearLevel,
                c.NameofSchool as ChildNameofSchool, c.AddressofSchool as ChildAddressofSchool,
                c.ChSchoolID
            FROM studentinformation s
            LEFT JOIN applicantinformation a ON s.StudID = a.StudID
            LEFT JOIN parentguardianinfo p ON s.StudID = p.StudID
            LEFT JOIN childreninfo c ON a.AppID = c.AppID
            WHERE s.StudEmail = ?
            ORDER BY a.AppDate DESC LIMIT 1
        """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    data = new FormData();
                    
                    // Student Information
                    data.setStudID(rs.getString("StudID"));
                    data.setStudAge(rs.getInt("StudAge"));
                    data.setStudBirthdate(rs.getDate("StudBirthdate"));
                    data.setStudCitizenship(rs.getString("StudCitizenship"));
                    data.setStudCivilStatus(rs.getString("StudCivilStatus"));
                    data.setStudReligion(rs.getString("StudReligion"));
                    data.setStudPermanentAdd(rs.getString("StudPermanentAdd"));
                    data.setStudCellNo(rs.getString("StudCellNo"));
                    data.setStudEmail(rs.getString("StudEmail"));
                    data.setDegreeProgram(rs.getString("DegreeProgram"));
                    data.setGwa(rs.getDouble("GWA"));
                    data.setSchoolID(rs.getString("SchoolID"));
                    data.setSchoolName(rs.getString("SchoolName"));
                    data.setSchoolAdd(rs.getString("SchoolAdd"));
                    data.setSchoolEmail(rs.getString("SchoolEmail"));
                    data.setStudSex(rs.getString("StudSex"));

                    // Application Information
                    data.setAppID(rs.getString("AppID"));

                    // Parent Information
                    data.setParentID(rs.getString("ParentID"));
                    data.setParentType(rs.getString("ParentType"));
                    data.setParentName(rs.getString("ParentName"));
                    data.setParentBirthdate(rs.getDate("ParentBirthdate"));
                    data.setParentCellNo(rs.getString("ParentCellNo"));
                    data.setParentEmail(rs.getString("ParentEmail"));
                    data.setParentPermanentAdd(rs.getString("ParentPermanentAdd"));
                    data.setParentCivilStatus(rs.getString("ParentCivilStatus"));
                    data.setParentCitizenship(rs.getString("ParentCitizenship"));
                    data.setParentOccupation(rs.getString("Occupation"));
                    data.setParentEducationalAttainment(rs.getString("EducAttainment"));
                    data.setParentMonthlyIncome(rs.getString("MonthlyIncome"));

                    // Child Information
                    String childName = rs.getString("ChildName");
                    if (childName != null) {
                        String[] childNameParts = childName.split(" ");
                        data.setChildFirstName(childNameParts.length > 0 ? childNameParts[0] : "");
                        data.setChildMiddleName(childNameParts.length > 1 ? childNameParts[1] : "");
                        data.setChildLastName(childNameParts.length > 2 ? childNameParts[2] : "");
                    }
                    data.setChildDateOfBirth(rs.getDate("ChildBirthDate"));
                    data.setChildSex(rs.getString("ChildSex"));
                    data.setChildAge(rs.getString("ChildAge"));
                    data.setChildGradeYearLevel(rs.getString("GradeYearLevel"));
                    data.setChildNameOfSchool(rs.getString("ChildNameofSchool"));
                    data.setChildAddressOfSchool(rs.getString("ChildAddressofSchool"));
                    data.setChSchoolID(rs.getString("ChSchoolID"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    // Fetch the StudID for a user by their email (from studentinformation)
    public String getStudIDByEmail(String email) {
        String sql = "SELECT StudID FROM studentinformation WHERE StudEmail = ? LIMIT 1";
        try (Connection conn = getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (java.sql.ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("StudID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Generate a new AppID in the format EDU-XXXX
    public String generateNewAppID() {
        String prefix = "EDU-";
        String sql = "SELECT AppID FROM applicantinformation WHERE AppID LIKE ? ORDER BY AppID DESC LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, prefix + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String lastID = rs.getString("AppID");
                    int num = Integer.parseInt(lastID.substring(prefix.length()));
                    return prefix + String.format("%04d", num + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prefix + "0001"; // Default if no IDs exist
    }

    // Insert a new application record for a new user
    public void createInitialApplicationForUser(String email) {
        String studID = getStudIDByEmail(email);
        if (studID == null) return;
        String appID = generateNewAppID();
        String sql = "INSERT INTO applicantinformation (AppID, StudID, AppDate, AppStatus) VALUES (?, ?, NOW(), 'Pending')";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appID);
            stmt.setString(2, studID);
            stmt.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Insert a new student record with minimal info (name, email) for registration
    public void createStudentInfoForRegistration(String name, String email) {
        String newStudID = generateNewStudentID();
        // The 'StudName' column caused an error, so it's removed. The 'name' parameter is currently unused.
        String sql = "INSERT INTO studentinformation (StudID, StudEmail) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newStudID);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Generate a new StudID in the format STUD-XXXX
    public String generateNewStudentID() {
        String prefix = "STUD-";
        String sql = "SELECT StudID FROM studentinformation WHERE StudID LIKE ? ORDER BY StudID DESC LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, prefix + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String lastID = rs.getString("StudID");
                    int num = Integer.parseInt(lastID.substring(prefix.length()));
                    return prefix + String.format("%04d", num + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prefix + "0001"; // Default if no IDs exist
    }

    // Check if a StudID already exists
    public boolean studentIDExists(String studID) {
        String sql = "SELECT COUNT(*) FROM studentinformation WHERE StudID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Fetch the AppID for a user by their StudID (from applicantinformation)
    public String getAppIDByStudID(String studID) {
        String sql = "SELECT AppID FROM applicantinformation WHERE StudID = ? ORDER BY AppDate DESC LIMIT 1";
        try (Connection conn = getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studID);
            try (java.sql.ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("AppID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Generate a new ParentID in the format PRNT-XXXX
    public String generateNewParentID() {
        String prefix = "PRNT-";
        String sql = "SELECT ParentID FROM parentguardianinfo WHERE ParentID LIKE ? ORDER BY ParentID DESC LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, prefix + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String lastID = rs.getString("ParentID");
                    int num = Integer.parseInt(lastID.substring(prefix.length()));
                    return prefix + String.format("%04d", num + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prefix + "0001"; // Default if no IDs exist
    }

    // Check if a ParentID already exists
    public boolean parentIDExists(String parentID) {
        String sql = "SELECT COUNT(*) FROM parentguardianinfo WHERE ParentID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, parentID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
} 