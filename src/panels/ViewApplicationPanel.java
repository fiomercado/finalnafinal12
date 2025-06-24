package panels;

import swing.UIUtils;
import model.FormData;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.text.SimpleDateFormat;

public class ViewApplicationPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String loginEmail;

    // Fields (same as FillOutFormPanel)
    private JTextField firstNameField, middleNameField, lastNameField, suffixField, appIDField;
    private JComboBox<String> houseTypeField;
    private JTextField studIDField, studAgeField, studBirthdateField, studCitizenshipField, studReligionField, studPermanentAddField, studCellNoField, studEmailField, degreeProgramField, gwaField, schoolIDField, schoolNameField, schoolAddField, schoolEmailField;
    private JComboBox<String> studCivilStatusField, studSexField;
    private JTextField parentIDField, parentNameField, parentBirthdateField, parentCitizenshipField, parentOccupationField, parentMonthlyIncomeField, parentEducationalAttainmentField, parentCellNoField, parentEmailField, parentPermanentAddField;
    private JComboBox<String> parentTypeField, parentCivilStatusField;
    private JTextField childFirstNameField, childMiddleNameField, childLastNameField, childBirthDateField, childAgeField, childGradeYearLevelField, childNameOfSchoolField, childAddressOfSchoolField, chSchoolIDField;
    private JComboBox<String> childSexField;

    public ViewApplicationPanel(CardLayout cardLayout, JPanel mainPanel, String loginEmail) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.loginEmail = loginEmail;
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        // Personal Information fields
        firstNameField = createReadOnlyField(15);
        middleNameField = createReadOnlyField(15);
        lastNameField = createReadOnlyField(15);
        suffixField = createReadOnlyField(10);
        appIDField = createReadOnlyField(15);
        houseTypeField = createReadOnlyComboBox(new String[]{"Owned", "Rented", "Shared"});
        houseTypeField.setEnabled(false);

        // Student Information fields
        studIDField = createReadOnlyField(15);
        studAgeField = createReadOnlyField(8);
        studBirthdateField = createReadOnlyField(10);
        studCitizenshipField = createReadOnlyField(15);
        studReligionField = createReadOnlyField(15);
        studPermanentAddField = createReadOnlyField(25);
        studCellNoField = createReadOnlyField(15);
        studEmailField = createReadOnlyField(20);
        degreeProgramField = createReadOnlyField(20);
        gwaField = createReadOnlyField(10);
        schoolIDField = createReadOnlyField(15);
        schoolNameField = createReadOnlyField(20);
        schoolAddField = createReadOnlyField(25);
        schoolEmailField = createReadOnlyField(20);
        studCivilStatusField = createReadOnlyComboBox(new String[]{"Single", "Married", "Divorced", "Widowed"});
        studCivilStatusField.setEnabled(false);
        studSexField = createReadOnlyComboBox(new String[]{"Male", "Female", "Other"});
        studSexField.setEnabled(false);

        // Parent info fields
        parentIDField = createReadOnlyField(15);
        parentTypeField = createReadOnlyComboBox(new String[]{"Father", "Mother", "Guardian"});
        parentTypeField.setEnabled(false);
        parentNameField = createReadOnlyField(20);
        parentBirthdateField = createReadOnlyField(10);
        parentCitizenshipField = createReadOnlyField(15);
        parentCivilStatusField = createReadOnlyComboBox(new String[]{"Single", "Married", "Divorced", "Widowed"});
        parentCivilStatusField.setEnabled(false);
        parentOccupationField = createReadOnlyField(15);
        parentMonthlyIncomeField = createReadOnlyField(15);
        parentEducationalAttainmentField = createReadOnlyField(25);
        parentCellNoField = createReadOnlyField(15);
        parentEmailField = createReadOnlyField(20);
        parentPermanentAddField = createReadOnlyField(25);

        // Child info fields
        childFirstNameField = createReadOnlyField(15);
        childMiddleNameField = createReadOnlyField(15);
        childLastNameField = createReadOnlyField(15);
        childBirthDateField = createReadOnlyField(10);
        childAgeField = createReadOnlyField(8);
        childSexField = createReadOnlyComboBox(new String[]{"Male", "Female", "Other"});
        childSexField.setEnabled(false);
        childGradeYearLevelField = createReadOnlyField(10);
        childNameOfSchoolField = createReadOnlyField(25);
        childAddressOfSchoolField = createReadOnlyField(25);
        chSchoolIDField = createReadOnlyField(15);
    }

    private JTextField createReadOnlyField(int columns) {
        JTextField field = UIUtils.createTextField(columns);
        field.setEditable(false);
        field.setBackground(new Color(245, 245, 245));
        return field;
    }
    private JComboBox<String> createReadOnlyComboBox(String[] options) {
        JComboBox<String> combo = UIUtils.createComboBox(options);
        combo.setEnabled(false);
        return combo;
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        setBackground(UIUtils.BACKGROUND_COLOR);
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        LineBorder boldBlackBorder = new LineBorder(Color.BLACK, 2);
        JPanel personalStudentPanel = createPersonalAndStudentInfoPanel();
        personalStudentPanel.setBorder(BorderFactory.createTitledBorder(boldBlackBorder, "Personal and Student Information"));
        formPanel.add(personalStudentPanel);
        formPanel.add(Box.createVerticalStrut(8));
        JPanel parentPanel = createParentPanel();
        parentPanel.setBorder(BorderFactory.createTitledBorder(boldBlackBorder, "Parent Information"));
        formPanel.add(parentPanel);
        formPanel.add(Box.createVerticalStrut(8));
        JPanel childPanel = createChildPanel();
        childPanel.setBorder(BorderFactory.createTitledBorder(boldBlackBorder, "Children Information"));
        formPanel.add(childPanel);
        JButton backButton = UIUtils.createSecondaryButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        JPanel buttonPanel = UIUtils.createFooterPanel(backButton);
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().setPreferredSize(new Dimension(800, 600));
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
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
        panel.add(new JLabel("Birthdate (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        panel.add(childBirthDateField, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 3;
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
        panel.add(new JLabel("Name of School:"), gbc);
        gbc.gridx = 5;
        panel.add(childNameOfSchoolField, gbc);
        row++;
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

    public void setFormData(FormData data) {
        if (data == null) return;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // Personal Information
        if (data.getStudName() != null) {
            String[] nameParts = data.getStudName().split(" ");
            firstNameField.setText(nameParts.length > 0 ? nameParts[0] : "");
            middleNameField.setText(nameParts.length > 2 ? nameParts[1] : "");
            lastNameField.setText(nameParts.length > 2 ? nameParts[2] : (nameParts.length > 1 ? nameParts[1] : ""));
            suffixField.setText(nameParts.length > 3 ? nameParts[3] : "");
        }
        houseTypeField.setSelectedItem(data.getHouseType());
        // Student Information
        studIDField.setText(data.getStudID());
        studAgeField.setText(data.getStudAge() != 0 ? String.valueOf(data.getStudAge()) : "");
        studBirthdateField.setText(data.getStudBirthdate() != null ? dateFormat.format(data.getStudBirthdate()) : "");
        studCitizenshipField.setText(data.getStudCitizenship());
        studCivilStatusField.setSelectedItem(data.getStudCivilStatus());
        studReligionField.setText(data.getStudReligion());
        studPermanentAddField.setText(data.getStudPermanentAdd());
        studCellNoField.setText(data.getStudCellNo());
        studEmailField.setText(data.getStudEmail());
        degreeProgramField.setText(data.getDegreeProgram());
        gwaField.setText(data.getGwa() != 0.0 ? String.valueOf(data.getGwa()) : "");
        schoolIDField.setText(data.getSchoolID());
        schoolNameField.setText(data.getSchoolName());
        schoolAddField.setText(data.getSchoolAdd());
        schoolEmailField.setText(data.getSchoolEmail());
        studSexField.setSelectedItem(data.getStudSex());
        // Parent Information
        parentIDField.setText(data.getParentID());
        parentTypeField.setSelectedItem(data.getParentType());
        parentNameField.setText(data.getParentName());
        parentBirthdateField.setText(data.getParentBirthdate() != null ? dateFormat.format(data.getParentBirthdate()) : "");
        parentCitizenshipField.setText(data.getParentCitizenship());
        parentCivilStatusField.setSelectedItem(data.getParentCivilStatus());
        parentOccupationField.setText(data.getParentOccupation());
        parentMonthlyIncomeField.setText(data.getParentMonthlyIncome());
        parentEducationalAttainmentField.setText(data.getParentEducationalAttainment());
        parentCellNoField.setText(data.getParentCellNo());
        parentEmailField.setText(data.getParentEmail());
        parentPermanentAddField.setText(data.getParentPermanentAdd());
        // Child Information
        childFirstNameField.setText(data.getChildFirstName());
        childMiddleNameField.setText(data.getChildMiddleName());
        childLastNameField.setText(data.getChildLastName());
        childBirthDateField.setText(data.getChildDateOfBirth() != null ? dateFormat.format(data.getChildDateOfBirth()) : "");
        childAgeField.setText(data.getChildAge());
        childSexField.setSelectedItem(data.getChildSex());
        childGradeYearLevelField.setText(data.getChildGradeYearLevel());
        childNameOfSchoolField.setText(data.getChildNameOfSchool());
        childAddressOfSchoolField.setText(data.getChildAddressOfSchool());
        chSchoolIDField.setText(data.getChSchoolID());
    }
}