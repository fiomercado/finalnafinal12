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

    // Personal Information fields (for applicantinformation table)
    private JTextField firstNameField;
    private JTextField middleNameField;
    private JTextField lastNameField;
    private JTextField suffixField;
    private JTextField addressField;
    private JComboBox<String> houseTypeField;
    private JTextField incomeField;
    private JTextField appIDField;

    // Student Information fields (for studentinformation table)
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

    // Parent info fields (for parentguardianinfo table)
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

    // Child info fields (for childreninfo table)
    private JTextField childFirstNameField;
    private JTextField childMiddleNameField;
    private JTextField childLastNameField;
    private JTextField childSuffixField;
    private JTextField childBirthDateField;
    private JTextField childAgeField;
    private JComboBox<String> childSexField;
    private JTextField childGradeYearLevelField;
    private JTextField childNameOfSchoolField;
    private JTextField childAddressOfSchoolField;
    private JTextField chSchoolIDField;

    // Navigation components
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Consumer<FormData> onFormSubmit;

    // Edit mode tracking
    private boolean editMode = false;
    private String editingStudID = null;
    private String editingAppID = null;

    private String loginEmail;
    private FormData currentFormData;

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
        addressField = UIUtils.createTextField(25);
        incomeField = UIUtils.createTextField(15);
        appIDField = UIUtils.createTextField(15);
        appIDField.setEditable(false);

        // Student Information fields
        studIDField = UIUtils.createTextField(15);
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
        childSuffixField = UIUtils.createTextField(10);
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
        gbc.insets = new Insets(2, 4, 2, 4); // Tighter spacing
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;

        int row = 0;
        
        // Row 1 - First Name, Middle Name, Last Name
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
        
        // Row 5 - Address, Permanent Address, Income
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        panel.add(addressField, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Permanent Address:"), gbc);
        gbc.gridx = 3;
        panel.add(studPermanentAddField, gbc);
        gbc.gridx = 4;
        panel.add(new JLabel("Income:"), gbc);
        gbc.gridx = 5;
        panel.add(incomeField, gbc);
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
        panel.add(new JLabel("App ID (EDU-XXXX):"), gbc);
        gbc.gridx = 5;
        panel.add(appIDField, gbc);
        
        return panel;
    }

    // Make parent and child panels more compact
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
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Suffix:"), gbc);
        gbc.gridx = 1;
        panel.add(childSuffixField, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Birth Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 3;
        panel.add(childBirthDateField, gbc);
        gbc.gridx = 4;
        panel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 5;
        panel.add(childAgeField, gbc);
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Sex:"), gbc);
        gbc.gridx = 1;
        panel.add(childSexField, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Grade/Year Level:"), gbc);
        gbc.gridx = 3;
        panel.add(childGradeYearLevelField, gbc);
        gbc.gridx = 4;
        panel.add(new JLabel("School Name:"), gbc);
        gbc.gridx = 5;
        panel.add(childNameOfSchoolField, gbc);
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("School Address:"), gbc);
        gbc.gridx = 1;
        panel.add(childAddressOfSchoolField, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Child School ID:"), gbc);
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
        addressField.setText(data.getAddress() != null ? data.getAddress() : "");
        incomeField.setText(data.getIncome() != null ? data.getIncome() : "");
        appIDField.setText(data.getAppID() != null ? data.getAppID() : "");

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

        // Set combo box values
        if (data.getHouseType() != null) {
            houseTypeField.setSelectedItem(data.getHouseType());
        }
        if (data.getStudCivilStatus() != null) {
            studCivilStatusField.setSelectedItem(convertCivilStatusToDisplay(data.getStudCivilStatus()));
        }
        if (data.getStudSex() != null) {
            studSexField.setSelectedItem(convertSexToDisplay(data.getStudSex()));
        }

        // Parent Information
        parentIDField.setText(data.getParentID() != null ? data.getParentID() : "");
        if (data.getParentType() != null) {
            parentTypeField.setSelectedItem(convertParentTypeToDisplay(data.getParentType()));
        }
        parentNameField.setText(data.getParentName() != null ? data.getParentName() : "");
        if (data.getParentBirthdate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            parentBirthdateField.setText(sdf.format(data.getParentBirthdate()));
        }
        parentCitizenshipField.setText(data.getParentCitizenship() != null ? data.getParentCitizenship() : "");
        if (data.getParentCivilStatus() != null) {
            parentCivilStatusField.setSelectedItem(convertCivilStatusToDisplay(data.getParentCivilStatus()));
        }
        parentOccupationField.setText(data.getParentOccupation() != null ? data.getParentOccupation() : "");
        parentMonthlyIncomeField.setText(data.getParentMonthlyIncome() != null ? data.getParentMonthlyIncome() : "");
        parentEducationalAttainmentField.setText(data.getParentEducationalAttainment() != null ? data.getParentEducationalAttainment() : "");
        parentCellNoField.setText(data.getParentCellNo() != null ? data.getParentCellNo() : "");
        parentEmailField.setText(data.getParentEmail() != null ? data.getParentEmail() : "");
        parentPermanentAddField.setText(data.getParentPermanentAdd() != null ? data.getParentPermanentAdd() : "");

        // Child Information
        childFirstNameField.setText(data.getChildFirstName() != null ? data.getChildFirstName() : "");
        childMiddleNameField.setText(data.getChildMiddleName() != null ? data.getChildMiddleName() : "");
        childLastNameField.setText(data.getChildLastName() != null ? data.getChildLastName() : "");
        childSuffixField.setText(data.getChildSuffix() != null ? data.getChildSuffix() : "");
        if (data.getChildDateOfBirth() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            childBirthDateField.setText(sdf.format(data.getChildDateOfBirth()));
        }
        childAgeField.setText(data.getChildAge() != null ? data.getChildAge() : "");
        if (data.getChildSex() != null) {
            childSexField.setSelectedItem(convertSexToDisplay(data.getChildSex()));
        }
        childGradeYearLevelField.setText(data.getChildGradeYearLevel() != null ? data.getChildGradeYearLevel() : "");
        childNameOfSchoolField.setText(data.getChildNameOfSchool() != null ? data.getChildNameOfSchool() : "");
        childAddressOfSchoolField.setText(data.getChildAddressOfSchool() != null ? data.getChildAddressOfSchool() : "");
        chSchoolIDField.setText(data.getChSchoolID() != null ? data.getChSchoolID() : "");
    }

    public FormData collectFormData() {
        FormData data = new FormData();
        
        // Personal Information
        data.setFirstName(firstNameField.getText());
        data.setMiddleName(middleNameField.getText());
        data.setLastName(lastNameField.getText());
        data.setSuffix(suffixField.getText());
        data.setAddress(addressField.getText());
        data.setHouseType((String) houseTypeField.getSelectedItem());
        data.setIncome(incomeField.getText());
        data.setAppID(appIDField.getText());

        // Student Information
        data.setStudID(studIDField.getText());
        try {
            data.setStudAge(Integer.parseInt(studAgeField.getText()));
        } catch (NumberFormatException e) {
            data.setStudAge(0);
        }
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            data.setStudBirthdate(new java.sql.Date(sdf.parse(studBirthdateField.getText()).getTime()));
        } catch (ParseException e) {
            data.setStudBirthdate(null);
        }
        
        data.setStudCitizenship(studCitizenshipField.getText());
        data.setStudCivilStatus(convertCivilStatusToDB((String) studCivilStatusField.getSelectedItem()));
        data.setStudReligion(studReligionField.getText());
        data.setStudPermanentAdd(studPermanentAddField.getText());
        data.setStudCellNo(studCellNoField.getText());
        data.setStudEmail(studEmailField.getText());
        data.setDegreeProgram(degreeProgramField.getText());
        
        try {
            data.setGwa(Double.parseDouble(gwaField.getText()));
        } catch (NumberFormatException e) {
            data.setGwa(0.0);
        }
        
        data.setSchoolID(schoolIDField.getText());
        data.setSchoolName(schoolNameField.getText());
        data.setSchoolAdd(schoolAddField.getText());
        data.setSchoolEmail(schoolEmailField.getText());
        data.setStudSex(convertSexToDB((String) studSexField.getSelectedItem()));

        // Parent Information
        data.setParentID(parentIDField.getText());
        data.setParentType(convertParentTypeToDB((String) parentTypeField.getSelectedItem()));
        data.setParentName(parentNameField.getText());
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            data.setParentBirthdate(new java.sql.Date(sdf.parse(parentBirthdateField.getText()).getTime()));
        } catch (ParseException e) {
            data.setParentBirthdate(null);
        }
        
        data.setParentCitizenship(parentCitizenshipField.getText());
        data.setParentCivilStatus(convertCivilStatusToDB((String) parentCivilStatusField.getSelectedItem()));
        data.setParentOccupation(parentOccupationField.getText());
        data.setParentMonthlyIncome(parentMonthlyIncomeField.getText());
        data.setParentEducationalAttainment(parentEducationalAttainmentField.getText());
        data.setParentCellNo(parentCellNoField.getText());
        data.setParentEmail(parentEmailField.getText());
        data.setParentPermanentAdd(parentPermanentAddField.getText());

        // Child Information
        data.setChildFirstName(childFirstNameField.getText());
        data.setChildMiddleName(childMiddleNameField.getText());
        data.setChildLastName(childLastNameField.getText());
        data.setChildSuffix(childSuffixField.getText());
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            data.setChildDateOfBirth(new java.sql.Date(sdf.parse(childBirthDateField.getText()).getTime()));
        } catch (ParseException e) {
            data.setChildDateOfBirth(null);
        }
        
        data.setChildAge(childAgeField.getText());
        data.setChildSex(convertSexToDB((String) childSexField.getSelectedItem()));
        data.setChildGradeYearLevel(childGradeYearLevelField.getText());
        data.setChildNameOfSchool(childNameOfSchoolField.getText());
        data.setChildAddressOfSchool(childAddressOfSchoolField.getText());
        data.setChSchoolID(chSchoolIDField.getText());

        return data;
    }

    public void saveFormDataToDatabase(FormData data) {
        // This method will be implemented to save the updated data to the database
        // For now, we'll use the same logic as FillOutFormPanel
        panels.FillOutFormPanel tempPanel = new panels.FillOutFormPanel(cardLayout, mainPanel, loginEmail, null);
        tempPanel.saveFormDataToDatabase(data);
    }

    public boolean validateForm() {
        // Basic validation - you can add more specific validation rules
        if (firstNameField.getText().trim().isEmpty() || lastNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "First Name and Last Name are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (studEmailField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email is required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
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
            default: return displayValue;
        }
    }

    private String convertSexToDB(String displayValue) {
        if (displayValue == null) return null;
        switch (displayValue) {
            case "Male": return "M";
            case "Female": return "F";
            case "Other": return "O";
            default: return displayValue;
        }
    }

    // Helper methods for converting database values to display values
    private String convertParentTypeToDisplay(String dbValue) {
        if (dbValue == null) return "Not specified";
        switch (dbValue) {
            case "F": return "Father";
            case "M": return "Mother";
            case "G": return "Guardian";
            default: return "Unknown";
        }
    }

    private String convertCivilStatusToDisplay(String dbValue) {
        if (dbValue == null) return "Not specified";
        switch (dbValue) {
            case "S": return "Single";
            case "M": return "Married";
            case "D": return "Divorced";
            case "W": return "Widowed";
            default: return "Other";
        }
    }

    private String convertSexToDisplay(String dbValue) {
        if (dbValue == null) return "Not specified";
        switch (dbValue) {
            case "M": return "Male";
            case "F": return "Female";
            case "O": return "Other";
            default: return "Not specified";
        }
    }
} 