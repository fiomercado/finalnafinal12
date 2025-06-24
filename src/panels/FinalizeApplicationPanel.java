package panels;

import swing.UIUtils;
import model.FormData;
import model.DatabaseManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.function.Consumer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.UUID;
import java.sql.ResultSet;

public class FinalizeApplicationPanel extends JPanel {

    // Personal Information fields
    private JTextField firstNameField;
    private JTextField middleNameField;
    private JTextField lastNameField;
    private JTextField suffixField;
    private JTextField appIDField;

    // Student Information fields
    private JTextField studIDField;
    private JTextField studAgeField;
    private JTextField studBirthdateField;
    private JTextField studCitizenshipField;
    private JComboBox<String> studCivilStatusField;
    private JTextField studReligionField;
    private JTextField studPermanentAddField;
    private JTextField studCellNoField;
    private JTextField studEmailField;
    private JTextField degreeProgramField;
    private JTextField gwaField;
    private JTextField schoolIDField;
    private JTextField schoolNameField;
    private JTextField schoolAddField;
    private JTextField schoolEmailField;
    private JComboBox<String> studSexField;

    // Parent info fields
    private JTextField parentIDField;
    private JComboBox<String> parentTypeField;
    private JTextField parentNameField;
    private JTextField parentBirthdateField;
    private JTextField parentCitizenshipField;
    private JComboBox<String> parentCivilStatusField;
    private JTextField parentOccupationField;
    private JTextField parentMonthlyIncomeField;
    private JTextField parentEducationalAttainmentField;
    private JTextField parentCellNoField;
    private JTextField parentEmailField;
    private JTextField parentPermanentAddField;

    // Child info fields
    private JTextField childFirstNameField;
    private JTextField childMiddleNameField;
    private JTextField childLastNameField;
    private JTextField childBirthDateField;
    private JTextField childAgeField;
    private JComboBox<String> childSexField;
    private JTextField childGradeYearLevelField;
    private JTextField childNameOfSchoolField;
    private JTextField childAddressOfSchoolField;
    private JTextField chSchoolIDField;

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String loginEmail;
    private FormData currentFormData;
    private JComboBox<String> houseTypeField;

    public FinalizeApplicationPanel(CardLayout cardLayout, JPanel mainPanel, String userEmail) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.loginEmail = userEmail;

        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        // Personal Information fields
        firstNameField = UIUtils.createTextField(15);
        middleNameField = UIUtils.createTextField(15);
        lastNameField = UIUtils.createTextField(15);
        suffixField = UIUtils.createTextField(10);
        appIDField = UIUtils.createTextField(15);
        appIDField.setEditable(false);

        // Student Information fields
        studIDField = UIUtils.createTextField(15);
        studIDField.setEditable(false);
        studAgeField = UIUtils.createTextField(8);
        studBirthdateField = UIUtils.createTextField(10);
        studCitizenshipField = UIUtils.createTextField(15);
        studReligionField = UIUtils.createTextField(15);
        studPermanentAddField = UIUtils.createTextField(25);
        studCellNoField = UIUtils.createTextField(15);
        studEmailField = UIUtils.createTextField(20);
        if (loginEmail != null) {
            studEmailField.setText(loginEmail);
            studEmailField.setEditable(false);
        }
        gwaField = UIUtils.createTextField(10);
        degreeProgramField = UIUtils.createTextField(20);
        schoolIDField = UIUtils.createTextField(15);
        schoolNameField = UIUtils.createTextField(20);
        schoolAddField = UIUtils.createTextField(25);
        schoolEmailField = UIUtils.createTextField(20);

        // Parent info fields
        parentIDField = UIUtils.createTextField(15);
        parentIDField.setEditable(false);
        parentTypeField = UIUtils.createComboBox(new String[]{"Father", "Mother", "Guardian"});
        parentNameField = UIUtils.createTextField(20);
        parentBirthdateField = UIUtils.createTextField(10);
        parentCitizenshipField = UIUtils.createTextField(15);
        parentCivilStatusField = UIUtils.createComboBox(new String[]{"Single", "Married", "Divorced", "Widowed"});
        parentOccupationField = UIUtils.createTextField(15);
        parentMonthlyIncomeField = UIUtils.createTextField(15);
        parentEducationalAttainmentField = UIUtils.createTextField(25);
        parentCellNoField = UIUtils.createTextField(15);
        parentEmailField = UIUtils.createTextField(20);
        parentPermanentAddField = UIUtils.createTextField(25);

        // Child info fields
        childFirstNameField = UIUtils.createTextField(15);
        childMiddleNameField = UIUtils.createTextField(15);
        childLastNameField = UIUtils.createTextField(15);
        childBirthDateField = UIUtils.createTextField(10);
        childAgeField = UIUtils.createTextField(8);
        childGradeYearLevelField = UIUtils.createTextField(10);
        childNameOfSchoolField = UIUtils.createTextField(25);
        childAddressOfSchoolField = UIUtils.createTextField(25);
        chSchoolIDField = UIUtils.createTextField(15);

        // Initialize combo boxes
        String[] sexOptions = {"Male", "Female", "Other"};
        studSexField = UIUtils.createComboBox(sexOptions);
        childSexField = UIUtils.createComboBox(sexOptions);

        parentCivilStatusField = UIUtils.createComboBox(new String[]{"Single", "Married", "Divorced", "Widowed"});
        studCivilStatusField = UIUtils.createComboBox(new String[]{"Single", "Married", "Divorced", "Widowed"});
        String[] houseTypeOptions = {"Owned", "Rented", "Shared"};
        houseTypeField = UIUtils.createComboBox(houseTypeOptions);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        setBackground(UIUtils.BACKGROUND_COLOR);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a bold black LineBorder
        LineBorder boldBlackBorder = new LineBorder(Color.BLACK, 2);

        // Personal and Student Information Section
        JPanel personalStudentPanel = createPersonalAndStudentInfoPanel();
        personalStudentPanel.setBorder(BorderFactory.createTitledBorder(
                boldBlackBorder, "Personal and Student Information"));
        formPanel.add(personalStudentPanel);
        formPanel.add(Box.createVerticalStrut(8));

        // Parent Information Section
        JPanel parentPanel = createParentPanel();
        parentPanel.setBorder(BorderFactory.createTitledBorder(
                boldBlackBorder, "Parent/Guardian Information"));
        formPanel.add(parentPanel);
        formPanel.add(Box.createVerticalStrut(8));

        // Child Information Section
        JPanel childPanel = createChildPanel();
        childPanel.setBorder(BorderFactory.createTitledBorder(
                boldBlackBorder, "Child Information"));
        formPanel.add(childPanel);
        formPanel.add(Box.createVerticalStrut(8));

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setBackground(UIUtils.BACKGROUND_COLOR);

        JButton submitButton = UIUtils.createPrimaryButton("Submit Updated Application");
        JButton backButton = UIUtils.createSecondaryButton("Back to Home");

        submitButton.addActionListener(e -> {
            if (validateForm()) {
                FormData updatedData = collectFormData();
                saveFormDataToDatabase(updatedData);
                // Refresh ProfileInfoPanel to show updated submission date
                String userName = model.UserManager.getInstance().getUserByEmail(loginEmail) != null ? model.UserManager.getInstance().getUserByEmail(loginEmail).getName() : "";
                String appId = updatedData != null ? updatedData.getAppID() : null;
                // Remove old ProfileInfoPanel if it exists
                for (Component comp : mainPanel.getComponents()) {
                    if (comp instanceof ProfileInfoPanel) {
                        mainPanel.remove(comp);
                        break;
                    }
                }
                ProfileInfoPanel profilePanel = new ProfileInfoPanel(cardLayout, mainPanel, userName, loginEmail, appId);
                mainPanel.add(profilePanel, "Profile");
                JOptionPane.showMessageDialog(this,
                        "Application updated successfully! Thank you for updating your scholarship application.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(mainPanel, "Home");
            }
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        buttonsPanel.add(submitButton);
        buttonsPanel.add(backButton);

        // Create scroll pane for the form
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(scrollPane, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private JPanel createPersonalAndStudentInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UIUtils.BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 4, 2, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        int row = 0;
        // Row 1 - Student Name
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        panel.add(firstNameField, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Middle Name:"), gbc);
        gbc.gridx = 3;
        panel.add(middleNameField, gbc);
        gbc.gridx = 4;
        panel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 5;
        panel.add(lastNameField, gbc);
        row++;
        // Row 2 - Suffix, Age, Birthdate
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Suffix:"), gbc);
        gbc.gridx = 1;
        panel.add(suffixField, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 3;
        panel.add(studAgeField, gbc);
        gbc.gridx = 4;
        panel.add(new JLabel("Birthdate (YYYY-MM-DD):"), gbc);
        gbc.gridx = 5;
        panel.add(studBirthdateField, gbc);
        row++;
        // Row 3 - Civil Status, Religion, Sex
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Civil Status:"), gbc);
        gbc.gridx = 1;
        panel.add(studCivilStatusField, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Religion:"), gbc);
        gbc.gridx = 3;
        panel.add(studReligionField, gbc);
        gbc.gridx = 4;
        panel.add(new JLabel("Sex:"), gbc);
        gbc.gridx = 5;
        panel.add(studSexField, gbc);
        row++;
        // Row 4 - Email, Cell No, Citizenship
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        panel.add(studEmailField, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Cell No:"), gbc);
        gbc.gridx = 3;
        panel.add(studCellNoField, gbc);
        gbc.gridx = 4;
        panel.add(new JLabel("Citizenship:"), gbc);
        gbc.gridx = 5;
        panel.add(studCitizenshipField, gbc);
        row++;
        // Row 5 - Permanent Address
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Permanent Address:"), gbc);
        gbc.gridx = 1;
        panel.add(studPermanentAddField, gbc);
        row++;
        // Row 6 - House Type, Student ID, GWA
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("House Type:"), gbc);
        gbc.gridx = 1;
        panel.add(houseTypeField, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 3;
        panel.add(studIDField, gbc);
        gbc.gridx = 4;
        panel.add(new JLabel("GWA:"), gbc);
        gbc.gridx = 5;
        panel.add(gwaField, gbc);
        row++;
        // Row 7 - School ID, School Name, School Email
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("School ID:"), gbc);
        gbc.gridx = 1;
        panel.add(schoolIDField, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("School Name:"), gbc);
        gbc.gridx = 3;
        panel.add(schoolNameField, gbc);
        gbc.gridx = 4;
        panel.add(new JLabel("School Email:"), gbc);
        gbc.gridx = 5;
        panel.add(schoolEmailField, gbc);
        row++;
        // Row 8 - School Address, Degree Program, App ID
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("School Address:"), gbc);
        gbc.gridx = 1;
        panel.add(schoolAddField, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Degree Program:"), gbc);
        gbc.gridx = 3;
        panel.add(degreeProgramField, gbc);
        gbc.gridx = 4;
        panel.add(new JLabel("App ID:"), gbc);
        gbc.gridx = 5;
        panel.add(appIDField, gbc);
        return panel;
    }

    private JPanel createParentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UIUtils.BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 4, 2, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Parent ID:"), gbc);
        gbc.gridx = 1;
        panel.add(parentIDField, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Parent Type:"), gbc);
        gbc.gridx = 3;
        panel.add(parentTypeField, gbc);
        gbc.gridx = 4;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 5;
        panel.add(parentNameField, gbc);
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Birthdate (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        panel.add(parentBirthdateField, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Civil Status:"), gbc);
        gbc.gridx = 3;
        panel.add(parentCivilStatusField, gbc);
        gbc.gridx = 4;
        panel.add(new JLabel("Occupation:"), gbc);
        gbc.gridx = 5;
        panel.add(parentOccupationField, gbc);
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Nationality:"), gbc);
        gbc.gridx = 1;
        panel.add(parentCitizenshipField, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Educational Attainment:"), gbc);
        gbc.gridx = 3;
        panel.add(parentEducationalAttainmentField, gbc);
        gbc.gridx = 4;
        panel.add(new JLabel("Monthly Income:"), gbc);
        gbc.gridx = 5;
        panel.add(parentMonthlyIncomeField, gbc);
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Cell No:"), gbc);
        gbc.gridx = 1;
        panel.add(parentCellNoField, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 3;
        panel.add(parentEmailField, gbc);
        gbc.gridx = 4;
        panel.add(new JLabel("Permanent Address:"), gbc);
        gbc.gridx = 5;
        panel.add(parentPermanentAddField, gbc);
        return panel;
    }

    private JPanel createChildPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UIUtils.BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 4, 2, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        int row = 0;
        // Row 1 - First Name, Middle Name, Last Name
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        panel.add(childFirstNameField, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Middle Name:"), gbc);
        gbc.gridx = 3;
        panel.add(childMiddleNameField, gbc);
        gbc.gridx = 4;
        panel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 5;
        panel.add(childLastNameField, gbc);
        row++;
        // Row 2 - Birthdate, Age
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Birthdate (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        panel.add(childBirthDateField, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 3;
        panel.add(childAgeField, gbc);
        row++;
        // Row 3 - Sex, Grade/Year Level, Name of School
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Sex:"), gbc);
        gbc.gridx = 1;
        panel.add(childSexField, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Grade/Year Level:"), gbc);
        gbc.gridx = 3;
        panel.add(childGradeYearLevelField, gbc);
        gbc.gridx = 4;
        panel.add(new JLabel("Name of School:"), gbc);
        gbc.gridx = 5;
        panel.add(childNameOfSchoolField, gbc);
        row++;
        // Row 4 - Address of School, School ID
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Address of School:"), gbc);
        gbc.gridx = 1;
        panel.add(childAddressOfSchoolField, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("School ID:"), gbc);
        gbc.gridx = 3;
        panel.add(chSchoolIDField, gbc);
        return panel;
    }

    public void setFormData(FormData formData) {
        this.currentFormData = formData;
        prefillForm(formData);
    }

    public void prefillForm(FormData data) {
        if (data == null) return;

        // Personal Information
        firstNameField.setText(data.getFirstName() != null ? data.getFirstName() : "");
        middleNameField.setText(data.getMiddleName() != null ? data.getMiddleName() : "");
        lastNameField.setText(data.getLastName() != null ? data.getLastName() : "");
        suffixField.setText(data.getSuffix() != null ? data.getSuffix() : "");
        appIDField.setText(data.getAppID() != null ? data.getAppID() : "");
        houseTypeField.setSelectedItem(data.getHouseType() != null ? data.getHouseType() : "");

        // Student Information
        studIDField.setText(data.getStudID() != null ? data.getStudID() : "");
        studAgeField.setText(data.getStudAge() != 0 ? String.valueOf(data.getStudAge()) : "");
        if (data.getStudBirthdate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            studBirthdateField.setText(sdf.format(data.getStudBirthdate()));
        }
        studCitizenshipField.setText(data.getStudCitizenship() != null ? data.getStudCitizenship() : "");
        studReligionField.setText(data.getStudReligion() != null ? data.getStudReligion() : "");
        studPermanentAddField.setText(data.getStudPermanentAdd() != null ? data.getStudPermanentAdd() : "");
        studCellNoField.setText(data.getStudCellNo() != null ? data.getStudCellNo() : "");
        studEmailField.setText(data.getStudEmail() != null ? data.getStudEmail() : "");
        degreeProgramField.setText(data.getDegreeProgram() != null ? data.getDegreeProgram() : "");
        gwaField.setText(data.getGwa() != 0.0 ? String.valueOf(data.getGwa()) : "");
        schoolIDField.setText(data.getSchoolID() != null ? data.getSchoolID() : "");
        schoolNameField.setText(data.getSchoolName() != null ? data.getSchoolName() : "");
        schoolAddField.setText(data.getSchoolAdd() != null ? data.getSchoolAdd() : "");
        schoolEmailField.setText(data.getSchoolEmail() != null ? data.getSchoolEmail() : "");
        studCivilStatusField.setSelectedItem(convertCivilStatusToDisplay(data.getStudCivilStatus()));
        studSexField.setSelectedItem(convertSexToDisplay(data.getStudSex()));

        // Parent Information
        parentIDField.setText(data.getParentID() != null ? data.getParentID() : "");
        parentNameField.setText(data.getParentName() != null ? data.getParentName() : "");
        if (data.getParentBirthdate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            parentBirthdateField.setText(sdf.format(data.getParentBirthdate()));
        }
        parentCitizenshipField.setText(data.getParentCitizenship() != null ? data.getParentCitizenship() : "");
        parentOccupationField.setText(data.getParentOccupation() != null ? data.getParentOccupation() : "");
        parentMonthlyIncomeField.setText(data.getParentMonthlyIncome() != null ? data.getParentMonthlyIncome() : "");
        parentEducationalAttainmentField.setText(data.getParentEducationalAttainment() != null ? data.getParentEducationalAttainment() : "");
        parentCellNoField.setText(data.getParentCellNo() != null ? data.getParentCellNo() : "");
        parentEmailField.setText(data.getParentEmail() != null ? data.getParentEmail() : "");
        parentPermanentAddField.setText(data.getParentPermanentAdd() != null ? data.getParentPermanentAdd() : "");
        parentTypeField.setSelectedItem(convertParentTypeToDisplay(data.getParentType()));
        parentCivilStatusField.setSelectedItem(convertCivilStatusToDisplay(data.getParentCivilStatus()));

        // Child Information
        childFirstNameField.setText(data.getChildFirstName() != null ? data.getChildFirstName() : "");
        childMiddleNameField.setText(data.getChildMiddleName() != null ? data.getChildMiddleName() : "");
        childLastNameField.setText(data.getChildLastName() != null ? data.getChildLastName() : "");
        childBirthDateField.setText(data.getChildDateOfBirth() != null ? data.getChildDateOfBirth().toString() : "");
        childAgeField.setText(data.getChildAge() != null ? data.getChildAge() : "");
        childGradeYearLevelField.setText(data.getChildGradeYearLevel() != null ? data.getChildGradeYearLevel() : "");
        childNameOfSchoolField.setText(data.getChildNameOfSchool() != null ? data.getChildNameOfSchool() : "");
        childAddressOfSchoolField.setText(data.getChildAddressOfSchool() != null ? data.getChildAddressOfSchool() : "");
        chSchoolIDField.setText(data.getChSchoolID() != null ? data.getChSchoolID() : "");
        childSexField.setSelectedItem(convertSexToDisplay(data.getChildSex()));

        if (data.getStudName() != null) {
            String[] nameParts = data.getStudName().split(" ");
            firstNameField.setText(nameParts.length > 0 ? nameParts[0] : "");
            middleNameField.setText(nameParts.length > 2 ? nameParts[1] : "");
            lastNameField.setText(nameParts.length > 2 ? nameParts[2] : (nameParts.length > 1 ? nameParts[1] : ""));
            suffixField.setText(nameParts.length > 3 ? nameParts[3] : "");
        }
    }

    public FormData collectFormData() {
        FormData data = this.currentFormData;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Personal Information
            data.setFirstName(firstNameField.getText().trim());
            data.setMiddleName(middleNameField.getText().trim());
            data.setLastName(lastNameField.getText().trim());
            data.setSuffix(suffixField.getText().trim());
            data.setHouseType((String) houseTypeField.getSelectedItem());

            // Student Information
            data.setStudAge(Integer.parseInt(studAgeField.getText().trim()));
            data.setStudBirthdate(new java.sql.Date(dateFormat.parse(studBirthdateField.getText().trim()).getTime()));
            data.setStudCitizenship(studCitizenshipField.getText().trim());
            data.setStudCivilStatus(convertCivilStatusToDB((String) studCivilStatusField.getSelectedItem()));
            data.setStudReligion(studReligionField.getText().trim());
            data.setStudPermanentAdd(studPermanentAddField.getText().trim());
            data.setStudCellNo(studCellNoField.getText().trim());
            data.setDegreeProgram(degreeProgramField.getText().trim());
            data.setGwa(Double.parseDouble(gwaField.getText().trim()));
            data.setSchoolID(schoolIDField.getText().trim());
            data.setSchoolName(schoolNameField.getText().trim());
            data.setSchoolAdd(schoolAddField.getText().trim());
            data.setSchoolEmail(schoolEmailField.getText().trim());
            data.setStudSex(convertSexToDB((String) studSexField.getSelectedItem()));

            // Parent Information
            data.setParentType(convertParentTypeToDB((String) parentTypeField.getSelectedItem()));
            data.setParentName(parentNameField.getText().trim());
            String parentBirthdateStr = parentBirthdateField.getText().trim();
            if (!parentBirthdateStr.isEmpty()) {
                data.setParentBirthdate(new java.sql.Date(dateFormat.parse(parentBirthdateStr).getTime()));
            }
            data.setParentCitizenship(parentCitizenshipField.getText().trim());
            data.setParentCivilStatus(convertCivilStatusToDB((String) parentCivilStatusField.getSelectedItem()));
            data.setParentOccupation(parentOccupationField.getText().trim());
            data.setParentMonthlyIncome(parentMonthlyIncomeField.getText().trim());
            data.setParentEducationalAttainment(parentEducationalAttainmentField.getText().trim());
            data.setParentCellNo(parentCellNoField.getText().trim());
            data.setParentEmail(parentEmailField.getText().trim());
            data.setParentPermanentAdd(parentPermanentAddField.getText().trim());

            // Child Information
            data.setChildFirstName(childFirstNameField.getText().trim());
            data.setChildMiddleName(childMiddleNameField.getText().trim());
            data.setChildLastName(childLastNameField.getText().trim());
            String childBirthdateStr = childBirthDateField.getText().trim();
            if (!childBirthdateStr.isEmpty()) {
                data.setChildDateOfBirth(new java.sql.Date(dateFormat.parse(childBirthdateStr).getTime()));
            }
            data.setChildAge(childAgeField.getText().trim());
            data.setChildSex(convertSexToDB((String) childSexField.getSelectedItem()));
            data.setChildGradeYearLevel(childGradeYearLevelField.getText().trim());
            data.setChildNameOfSchool(childNameOfSchoolField.getText().trim());
            data.setChildAddressOfSchool(childAddressOfSchoolField.getText().trim());
            data.setChSchoolID(chSchoolIDField.getText().trim());

            String fullName = firstNameField.getText().trim();
            if (!middleNameField.getText().trim().isEmpty()) {
                fullName += " " + middleNameField.getText().trim();
            }
            if (!lastNameField.getText().trim().isEmpty()) {
                fullName += " " + lastNameField.getText().trim();
            }
            if (!suffixField.getText().trim().isEmpty()) {
                fullName += " " + suffixField.getText().trim();
            }
            data.setStudName(fullName);

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format for Age or GWA.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return data;
    }

    public void saveFormDataToDatabase(FormData data) {
        // Use the save method from FillOutFormPanel as it handles both insert and update
        FillOutFormPanel tempForm = new FillOutFormPanel(null, null, loginEmail, null);
        tempForm.prefillForm(data); // prefill to set edit mode and IDs
        tempForm.saveFormDataToDatabase(data);
    }

    public boolean validateForm() {
        // Add validation logic here
        return true;
    }

    // Helper methods for converting display values to database values
    private String convertParentTypeToDB(String displayValue) {
        if (displayValue == null) return null;
        switch (displayValue) {
            case "Father": return "F";
            case "Mother": return "M";
            case "Guardian": return "G";
            default: return displayValue;
        }
    }

    private String convertCivilStatusToDB(String displayValue) {
        if (displayValue == null) return null;
        switch (displayValue) {
            case "Single": return "S";
            case "Married": return "M";
            case "Divorced": return "D";
            case "Widowed": return "W";
            default: return "O";
        }
    }

    private String convertSexToDB(String displayValue) {
        if (displayValue == null) return null;
        switch (displayValue) {
            case "Male": return "M";
            case "Female": return "F";
            default: return "O";
        }
    }

    private String convertParentTypeToDisplay(String dbValue) {
        if (dbValue == null) return null;
        switch (dbValue) {
            case "F": return "Father";
            case "M": return "Mother";
            case "G": return "Guardian";
            default: return dbValue;
        }
    }

    private String convertCivilStatusToDisplay(String dbValue) {
        if (dbValue == null) return null;
        switch (dbValue) {
            case "S": return "Single";
            case "M": return "Married";
            case "D": return "Divorced";
            case "W": return "Widowed";
            default: return "Other";
        }
    }

    private String convertSexToDisplay(String dbValue) {
        if (dbValue == null) return null;
        switch (dbValue) {
            case "M": return "Male";
            case "F": return "Female";
            default: return "Other";
        }
    }
}
