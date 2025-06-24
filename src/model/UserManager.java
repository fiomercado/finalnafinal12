package model;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import panels.ProfileInfoPanel;

public class UserManager {
    private static final String USER_DATA_FILE = "users.txt";
    private static UserManager instance;
    private Map<String, UserData> users;

    private UserManager() {
        users = new HashMap<>();
        loadUsers();
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public static class UserData {
        private String name;
        private String email;
        private String password;
        private String appId;
        private String phone;
        private String address;

        public UserData(String name, String email, String password, String appId) {
            this(name, email, password, appId, "", "");
        }

        public UserData(String name, String email, String password, String appId, String phone, String address) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.appId = appId;
            this.phone = phone;
            this.address = address;
        }

        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getPassword() { return password; }
        public String getAppId() { return appId; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
    }

    public boolean registerUser(String name, String email, String password) {
        if (users.containsKey(email)) {
            throw new IllegalArgumentException("User with this email already exists.");
        }

        // Do NOT create student info on registration. This should only happen when the form is submitted.
        // model.DatabaseManager.getInstance().createStudentInfoForRegistration(name, email);

        // We should not create an application record on registration.
        // This will be done when the user fills out the form for the first time.
        String appId = null; // No AppID at registration

        UserData userData = new UserData(name, email, password, appId);
        users.put(email, userData);
        saveUsers();
        return true;
    }

    public UserData authenticateUser(String email, String password) {
        UserData userData = users.get(email);
        if (userData != null && userData.getPassword().equals(password)) {
            // Always fetch the latest AppID from the database on login
            String latestAppId = model.DatabaseManager.getInstance().getAppIDByEmail(email);
            System.out.println("DEBUG: Latest AppID for " + email + " is " + latestAppId);
            if (latestAppId != null && !latestAppId.equals(userData.getAppId())) {
                userData = new UserData(userData.getName(), userData.getEmail(), userData.getPassword(), latestAppId);
                users.put(email, userData);
                saveUsers();
            }
            System.out.println("DEBUG: AppID used for user " + email + " is " + userData.getAppId());
            return userData;
        }
        return null;
    }

    public boolean userExists(String email) {
        return users.containsKey(email);
    }

    public boolean deleteUser(String email) {
        if (!users.containsKey(email)) {
            return false;
        }

        users.remove(email);
        saveUsers();
        return true;
    }

    // Public getter for user data by email
    public UserData getUserByEmail(String email) {
        return users.get(email);
    }

    private void loadUsers() {
        File file = new File(USER_DATA_FILE);
        if (!file.exists()) {
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    String name = parts[0];
                    String email = parts[1];
                    String password = parts[2];
                    String appId = parts[3];
                    String phone = parts[4];
                    String address = parts[5];
                    users.put(email, new UserData(name, email, password, appId, phone, address));
                } else if (parts.length == 4) {
                    String name = parts[0];
                    String email = parts[1];
                    String password = parts[2];
                    String appId = parts[3];
                    users.put(email, new UserData(name, email, password, appId));
                } else if (parts.length == 3) { // fallback for old format
                    String name = parts[0];
                    String email = parts[1];
                    String password = parts[2];
                    users.put(email, new UserData(name, email, password, null));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
    }

    public void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_DATA_FILE))) {
            for (UserData userData : users.values()) {
                writer.println(userData.getName() + "|" + userData.getEmail() + "|" + userData.getPassword() + "|" + (userData.getAppId() != null ? userData.getAppId() : "") + "|" + (userData.getPhone() != null ? userData.getPhone() : "") + "|" + (userData.getAddress() != null ? userData.getAddress() : ""));
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
} 
