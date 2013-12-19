package org.kuali.student.poc.rules.population;

import org.kuali.student.r2.common.exceptions.DoesNotExistException;

/**
 * Represents people that will be queried about.
 * User: mahtabme
 * Date: 12/9/13
 * Time: 11:23 AM
 */
public enum PopulationPocPersonEnum implements hasPersonId {

    STUDENT1("2001", PopulationPocConstants.AFFILIATION_TYPE_STUDENT, "888020001", "student1", "kstone@kuali.edu", "student1", "kstone", true, "Kara", "Quentin", "Stone", "Female", "Ms.", "", "11 Walrus Circle", "", "West Newton", "MA", "02165", "1992-04-21", ""),
    STUDENT2("2002", PopulationPocConstants.AFFILIATION_TYPE_STUDENT, "888020002", "student2", "daleri@utoronto.ca", "student2", "daleri", true, "Daler", "", "Iqbal", "Male", "Mr.", "", "35 Penny Circle", "909", "Toronto", "ON", "M5V A4A", "1991-02-06", ""),
    STUDENT3("2003", PopulationPocConstants.AFFILIATION_TYPE_STUDENT, "888020003", "student3", "chowo@utoronto.ca", "student3", "chowo", true, "Oliver", "So", "Chow", "Male", "Mr.", "", "35 Spadina Crescent", "", "Toronto", "ON", "M5S 2A1", "1995-03-07", ""),
    STUDENT4("2004", PopulationPocConstants.AFFILIATION_TYPE_STUDENT, "888020004", "student4", "khant@utoronto.ca", "student4", "khant", true, "Tauhid", "", "Khan", "Male", "Mr.", "", "135 Calmwaters", "", "Toronto", "ON", "K5S 2A1", "1985-03-25", ""),
    STUDENT5("2005", PopulationPocConstants.AFFILIATION_TYPE_STUDENT, "888020005", "student5", "chowo2@utoronto.ca", "student5", "chowo2", true, "Oliver", "Se", "Chow", "Male", "Mr.", "", "35 Spadina Crescent", "", "Toronto", "ON", "X1X 5H5", "1995-05-24", ""),
    STUDENT6("2006", PopulationPocConstants.AFFILIATION_TYPE_STUDENT, "888020006", "student6", "chowj@utoronto.ca", "student6", "chowj", true, "James", "Kirk", "Chow", "Male", "Mr.", "", "83 Calms", "", "Toronto", "ON", "H5H Y2E", "1995-04-07", ""),
    STUDENT7("2007", PopulationPocConstants.AFFILIATION_TYPE_STUDENT, "888020007", "student7", "clauses@kuali.edu", "student7", "clauses", false, "Santa", "", "Clause", "Male", "Mr.", "", "1 North Pole", "", "Ellesmere Island", "YK", "H0H 0H0", "1990-09-09", ""),
    STUDENT8("2008", PopulationPocConstants.AFFILIATION_TYPE_STUDENT, "888020008", "student8", "kabulwa@utoronto.ca", "student8", "kabulwa", false, "Ahmed", "Kabir", "Kabulwallah", "Male", "Mr.", "", "135 Huronia Crescent", "", "Etobicoke", "ON", "L1K 3J3", "1999-10-19", ""),
    STUDENT9("2009", PopulationPocConstants.AFFILIATION_TYPE_STUDENT, "888020009", "student9", "talukdarm@utoronto.ca", "student9", "talukdarm", true, "Mannan", "Mohammad", "Talukdar", "Male", "Mr.", "", "24 Telfer Gardens", "", "Toronto", "ON", "M1H 3J5", "1980-05-23", ""),
    STUDENT10("2010", PopulationPocConstants.AFFILIATION_TYPE_STUDENT, "888020010", "student10", "kapoora@utoronto.ca", "student10", "kapoora", true, "Aamir", "Krishnachand", "Kapoor", "Male", "Mr.", "", "1 Ford Drive", "", "Toronto", "ON", "M5S 2B1", "1995-03-07", ""),
    STUDENT11("2011", PopulationPocConstants.AFFILIATION_TYPE_STUDENT, "888020011", "student11", "guptas@utoronto.ca", "student11", "guptas", true, "Sushmita", "Sen", "Gupta", "Female", "Ms.", "", "77 Clarke Boulevard", "", "Toronto", "ON", "N5S 6B1", "1995-06-22", ""),
    STUDENT12("2012", PopulationPocConstants.AFFILIATION_TYPE_STUDENT, "888020012", "student12", "solos@utoronto.ca", "student12", "solos", true, "Sushi", "San", "Solo", "Female", "Mrs.", "", "13 Elm Street", "", "North York", "ON", "O5S 4C1", "1995-12-12", ""),
    INSTRUCTOR1("3001", PopulationPocConstants.AFFILIATION_TYPE_INSTRUCTOR, "999030001", "instructor1", "clintonb@umd.edu", "instructor1", "clintonb", false, "Bill", "", "Clinton", "Male", "Mr.", "", "11 Washington Street", "", "New York City", "NY", "12345", "1952-04-21", ""),
    INSTRUCTOR2("3002", PopulationPocConstants.AFFILIATION_TYPE_INSTRUCTOR, "999030002", "instructor2", "gandhis@utoronto.ca", "instructor2", "gandhis", true, "Sabrina", "", "Gandhi", "Female", "Mrs.", "", "35 Huron Street", "", "Toronto", "ON", "M5S 1A1", "1953-02-06", "");

    /////////////////
    // PROPERTIES
    /////////////////

    private String personId;
    private String affiliation;
    private String governmentIssuedIdentificationNumber;
    private String principalId;
    private String principalName;
    private String entityId;
    private String password;
    private boolean active;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String title;
    private String suffix;
    private String street_name_number;
    private String apartment_number;
    private String city;
    private String state_province;
    private String zip_postal_code;
    private String birthDate;
    private String deceasedDate;

    //////////////////////////
    // CONSTRUCTOR
    //////////////////////////

    private PopulationPocPersonEnum (String personId,
                                     String affiliation,
                                     String governmentIssuedIdentificationNumber,
                                     String principalId,
                                     String principalName,
                                     String entityId,
                                     String password,
                                     boolean active,
                                     String firstName,
                                     String middleName,
                                     String lastName,
                                     String gender,
                                     String title,
                                     String suffix,
                                     String street_name_number,
                                     String apartment_number,
                                     String city,
                                     String state_province,
                                     String zip_postal_code,
                                     String birthDate,
                                     String deceasedDate) {
        this.personId = personId;
        this.affiliation = affiliation;
        this.governmentIssuedIdentificationNumber = governmentIssuedIdentificationNumber;
        this.principalId = principalId;
        this.principalName = principalName;
        this.entityId = entityId;
        this.password = password;
        this.active = active;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.title = title;
        this.suffix = suffix;
        this.street_name_number = street_name_number;
        this.apartment_number = apartment_number;
        this.city = city;
        this.state_province = state_province;
        this.zip_postal_code = zip_postal_code;
        this.birthDate = birthDate;
        this.deceasedDate = deceasedDate;
    }

    /////////////////////////////
    // GETTERS AND SETTERS
    /////////////////////////////

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getGovernmentIssuedIdentificationNumber() {
        return governmentIssuedIdentificationNumber;
    }

    public void setGovernmentIssuedIdentificationNumber(String governmentIssuedIdentificationNumber) {
        this.governmentIssuedIdentificationNumber = governmentIssuedIdentificationNumber;
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getStreet_name_number() {
        return street_name_number;
    }

    public void setStreet_name_number(String street_name_number) {
        this.street_name_number = street_name_number;
    }

    public String getApartment_number() {
        return apartment_number;
    }

    public void setApartment_number(String apartment_number) {
        this.apartment_number = apartment_number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState_province() {
        return state_province;
    }

    public void setState_province(String state_province) {
        this.state_province = state_province;
    }

    public String getZip_postal_code() {
        return zip_postal_code;
    }

    public void setZip_postal_code(String zip_postal_code) {
        this.zip_postal_code = zip_postal_code;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDeceasedDate() {
        return deceasedDate;
    }

    public void setDeceasedDate(String deceasedDate) {
        this.deceasedDate = deceasedDate;
    }

    /////////////////////////////
    // FUNCTIONALS
    /////////////////////////////

    public static PopulationPocPersonEnum getPerson (String personId) throws DoesNotExistException {
        for (PopulationPocPersonEnum person : PopulationPocPersonEnum.values()) {
            if (person.getPersonId()!=null && person.getPersonId().equals (personId)) {
                return person;
            }
        }
        throw new DoesNotExistException("Person with person id " + personId + " not found");
    }

    @Override
    public String toString() {
        return "PopulationPocPersonEnum{" +
                "personId='" + personId + '\'' +
                ", affiliation='" + affiliation + '\'' +
                ", governmentIssuedIdentificationNumber='" + governmentIssuedIdentificationNumber + '\'' +
                ", principalId='" + principalId + '\'' +
                ", principalName='" + principalName + '\'' +
                ", entityId='" + entityId + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", title='" + title + '\'' +
                ", suffix='" + suffix + '\'' +
                ", street_name_number='" + street_name_number + '\'' +
                ", apartment_number='" + apartment_number + '\'' +
                ", city='" + city + '\'' +
                ", state_province='" + state_province + '\'' +
                ", zip_postal_code='" + zip_postal_code + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", deceasedDate='" + deceasedDate + '\'' +
                '}';
    }

}
