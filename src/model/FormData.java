package model;

public class FormData {
    private String studID; // Matches StudID in studentinformation
    private String appID; // Application ID in format EDU-XXXX
    private java.sql.Date appDate; // Application date

    // Applicant Information fields (specific to applicantinformation table)
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;
    private String address;
    private String houseType;
    private String income;

    // Student Information fields (directly map to studentinformation table)
    private int studAge;
    private java.sql.Date studBirthdate;
    private String studCitizenship;
    private String studCivilStatus;
    private String studReligion;
    private String studPermanentAdd;
    private String studCellNo;
    private String studEmail;
    private String degreeProgram;
    private double gwa;
    private String schoolID;
    private String schoolName;
    private String schoolAdd;
    private String schoolEmail;
    private String studSex; // Added for student's sex
    private String studName;

    // Parent Information
    private String parentID; // Added for ParentID
    private String parentType; // Added for ParentType (F=Father, M=Mother, G=Guardian)
    private String parentName; // Added for Name
    private java.sql.Date parentBirthdate; // Added for Birthdate
    private String parentCitizenship;
    private String parentCivilStatus;
    private String parentOccupation;
    private String parentMonthlyIncome;
    private String parentEducationalAttainment;
    private String parentCellNo; // Added for parent's cell number
    private String parentEmail; // Added for Email
    private String parentPermanentAdd; // Added for parent's permanent address

    // Child Information
    private String childFirstName;
    private String childMiddleName;
    private String childLastName;
    private String childSuffix;
    private java.sql.Date childDateOfBirth;
    private String childAge;
    private String childSex;
    private String childGradeYearLevel;
    private String childNameOfSchool;
    private String childAddressOfSchool;
    private String chSchoolID;

    public FormData() {}

    // Student Information Getters and Setters
    public String getStudID() { return studID; }
    public void setStudID(String studID) { this.studID = studID; }

    public String getAppID() { return appID; }
    public void setAppID(String appID) { this.appID = appID; }

    public java.sql.Date getAppDate() { return appDate; }
    public void setAppDate(java.sql.Date appDate) { this.appDate = appDate; }

    public int getStudAge() { return studAge; }
    public void setStudAge(int studAge) { this.studAge = studAge; }

    public java.sql.Date getStudBirthdate() { return studBirthdate; }
    public void setStudBirthdate(java.sql.Date studBirthdate) { this.studBirthdate = studBirthdate; }

    public String getStudCitizenship() { return studCitizenship; }
    public void setStudCitizenship(String studCitizenship) { this.studCitizenship = studCitizenship; }

    public String getStudCivilStatus() { return studCivilStatus; }
    public void setStudCivilStatus(String studCivilStatus) { this.studCivilStatus = studCivilStatus; }

    public String getStudReligion() { return studReligion; }
    public void setStudReligion(String studReligion) { this.studReligion = studReligion; }

    public String getStudPermanentAdd() { return studPermanentAdd; }
    public void setStudPermanentAdd(String studPermanentAdd) { this.studPermanentAdd = studPermanentAdd; }

    public String getStudCellNo() { return studCellNo; }
    public void setStudCellNo(String studCellNo) { this.studCellNo = studCellNo; }

    public String getStudEmail() { return studEmail; }
    public void setStudEmail(String studEmail) { this.studEmail = studEmail; }

    public String getDegreeProgram() { return degreeProgram; }
    public void setDegreeProgram(String degreeProgram) { this.degreeProgram = degreeProgram; }

    public double getGwa() { return gwa; }
    public void setGwa(double gwa) { this.gwa = gwa; }

    public String getSchoolID() { return schoolID; }
    public void setSchoolID(String schoolID) { this.schoolID = schoolID; }

    public String getSchoolName() { return schoolName; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }

    public String getSchoolAdd() { return schoolAdd; }
    public void setSchoolAdd(String schoolAdd) { this.schoolAdd = schoolAdd; }

    public String getSchoolEmail() { return schoolEmail; }
    public void setSchoolEmail(String schoolEmail) { this.schoolEmail = schoolEmail; }

    public String getStudSex() { return studSex; }
    public void setStudSex(String studSex) { this.studSex = studSex; }

    public String getStudName() { return studName; }
    public void setStudName(String studName) { this.studName = studName; }

    // Applicant Information Getters and Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getSuffix() { return suffix; }
    public void setSuffix(String suffix) { this.suffix = suffix; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getHouseType() { return houseType; }
    public void setHouseType(String houseType) { this.houseType = houseType; }

    public String getIncome() { return income; }
    public void setIncome(String income) { this.income = income; }

    // Parent Information Getters and Setters (Streamlined)
    public String getParentID() { return parentID; }
    public void setParentID(String parentID) { this.parentID = parentID; }

    public String getParentType() { return parentType; }
    public void setParentType(String parentType) { this.parentType = parentType; }

    public String getParentName() { return parentName; }
    public void setParentName(String parentName) { this.parentName = parentName; }

    public java.sql.Date getParentBirthdate() { return parentBirthdate; }
    public void setParentBirthdate(java.sql.Date parentBirthdate) { this.parentBirthdate = parentBirthdate; }

    public String getParentCitizenship() { return parentCitizenship; }
    public void setParentCitizenship(String parentCitizenship) { this.parentCitizenship = parentCitizenship; }

    public String getParentCivilStatus() { return parentCivilStatus; }
    public void setParentCivilStatus(String parentCivilStatus) { this.parentCivilStatus = parentCivilStatus; }

    public String getParentOccupation() { return parentOccupation; }
    public void setParentOccupation(String parentOccupation) { this.parentOccupation = parentOccupation; }

    public String getParentMonthlyIncome() { return parentMonthlyIncome; }
    public void setParentMonthlyIncome(String parentMonthlyIncome) { this.parentMonthlyIncome = parentMonthlyIncome; }

    public String getParentEducationalAttainment() { return parentEducationalAttainment; }
    public void setParentEducationalAttainment(String parentEducationalAttainment) { this.parentEducationalAttainment = parentEducationalAttainment; }

    public String getParentCellNo() { return parentCellNo; }
    public void setParentCellNo(String parentCellNo) { this.parentCellNo = parentCellNo; }

    public String getParentEmail() { return parentEmail; }
    public void setParentEmail(String parentEmail) { this.parentEmail = parentEmail; }

    public String getParentPermanentAdd() { return parentPermanentAdd; }
    public void setParentPermanentAdd(String parentPermanentAdd) { this.parentPermanentAdd = parentPermanentAdd; }

    // Child Information Getters and Setters
    public String getChildFirstName() { return childFirstName; }
    public void setChildFirstName(String childFirstName) { this.childFirstName = childFirstName; }

    public String getChildMiddleName() { return childMiddleName; }
    public void setChildMiddleName(String childMiddleName) { this.childMiddleName = childMiddleName; }

    public String getChildLastName() { return childLastName; }
    public void setChildLastName(String childLastName) { this.childLastName = childLastName; }

    public String getChildSuffix() { return childSuffix; }
    public void setChildSuffix(String childSuffix) { this.childSuffix = childSuffix; }

    public java.sql.Date getChildDateOfBirth() { return childDateOfBirth; }
    public void setChildDateOfBirth(java.sql.Date childDateOfBirth) { this.childDateOfBirth = childDateOfBirth; }

    public String getChildAge() { return childAge; }
    public void setChildAge(String childAge) { this.childAge = childAge; }

    public String getChildSex() { return childSex; }
    public void setChildSex(String childSex) { this.childSex = childSex; }

    public String getChildGradeYearLevel() { return childGradeYearLevel; }
    public void setChildGradeYearLevel(String childGradeYearLevel) { this.childGradeYearLevel = childGradeYearLevel; }

    public String getChildNameOfSchool() { return childNameOfSchool; }
    public void setChildNameOfSchool(String childNameOfSchool) { this.childNameOfSchool = childNameOfSchool; }

    public String getChildAddressOfSchool() { return childAddressOfSchool; }
    public void setChildAddressOfSchool(String childAddressOfSchool) { this.childAddressOfSchool = childAddressOfSchool; }

    public String getChSchoolID() { return chSchoolID; }
    public void setChSchoolID(String chSchoolID) { this.chSchoolID = chSchoolID; }

    @Override
    public String toString() {
        return "FormData{" +
                "studID='" + studID + '\'' +
                ", appID='" + appID + '\'' +
                ", appDate=" + appDate +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", suffix='" + suffix + '\'' +
                ", address='" + address + '\'' +
                ", houseType='" + houseType + '\'' +
                ", income='" + income + '\'' +
                ", studAge=" + studAge +
                ", studBirthdate=" + studBirthdate +
                ", studCitizenship='" + studCitizenship + '\'' +
                ", studCivilStatus='" + studCivilStatus + '\'' +
                ", studReligion='" + studReligion + '\'' +
                ", studPermanentAdd='" + studPermanentAdd + '\'' +
                ", studCellNo='" + studCellNo + '\'' +
                ", studEmail='" + studEmail + '\'' +
                ", degreeProgram='" + degreeProgram + '\'' +
                ", gwa=" + gwa +
                ", schoolID='" + schoolID + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", schoolAdd='" + schoolAdd + '\'' +
                ", schoolEmail='" + schoolEmail + '\'' +
                ", studSex='" + studSex + '\'' +
                ", parentID='" + parentID + '\'' +
                ", parentType='" + parentType + '\'' +
                ", parentName='" + parentName + '\'' +
                ", parentBirthdate=" + parentBirthdate +
                ", parentCitizenship='" + parentCitizenship + '\'' +
                ", parentCivilStatus='" + parentCivilStatus + '\'' +
                ", parentOccupation='" + parentOccupation + '\'' +
                ", parentMonthlyIncome='" + parentMonthlyIncome + '\'' +
                ", parentEducationalAttainment='" + parentEducationalAttainment + '\'' +
                ", parentCellNo='" + parentCellNo + '\'' +
                ", parentEmail='" + parentEmail + '\'' +
                ", parentPermanentAdd='" + parentPermanentAdd + '\'' +
                ", childFirstName='" + childFirstName + '\'' +
                ", childMiddleName='" + childMiddleName + '\'' +
                ", childLastName='" + childLastName + '\'' +
                ", childSuffix='" + childSuffix + '\'' +
                ", childAge='" + childAge + '\'' +
                ", childSex='" + childSex + '\'' +
                ", childGradeYearLevel='" + childGradeYearLevel + '\'' +
                ", childNameOfSchool='" + childNameOfSchool + '\'' +
                ", childAddressOfSchool='" + childAddressOfSchool + '\'' +
                ", chSchoolID='" + chSchoolID + '\'' +
                '}';
    }
}
