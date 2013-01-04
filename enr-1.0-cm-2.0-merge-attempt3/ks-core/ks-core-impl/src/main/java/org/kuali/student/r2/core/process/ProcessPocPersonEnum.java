/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.process;


/**
 * @author nwright
 */
public enum ProcessPocPersonEnum {

    STUDENT1(2272,"STDNT", "888030001", "student1", "kstone@kuali.edu", "student1", "kstone", true, "Biochemistry", "Kara", "Q", "Stone", "Female", "Ms.", "", "11 Walrus Circle", "West Newton", "MA", "02165", "1952-04-21", "2011-12-01"),
    STUDENT2(2016, "STDNT", "888030002", "student2", "bharris@kuali.edu", "student2", "bharris", true, "Biology", "Barbara", "A", "Harris", "Female", "Ms.", "", "35 Penny Circle", "Boston", "MA", "02299", "1953-02-06", ""),
    STUDENT3(2397, "STDNT", "888030003", "student3", "criddle@kuali.edu", "student3", "criddle", true, "Botany", "Clifford", "I", "Riddle", "Male", "Mr.", "", "59 Atlantis Avenue", "Lynnfield", "MA", "01940", "1935-12-23", ""),
    STUDENT4(2166, "STDNT", "888030004", "student4", "nwelch@kuali.edu", "student4", "nwelch", true, "CareerServices", "Nina", "X", "Welch", "Female", "Ms.", "", "66 Gina Circle", "East Boston", "MA", "02128", "1966-05-07", ""),
    STUDENT5(2005, "STDNT", "888030005", "student5", "bmartin@kuali.edu", "student5", "bmartin", true, "Chemical", "Betty", "J", "Martin", "Female", "Ms.", "", "141 East 48th Avenue", "Boston", "MA", "02233", "1950-06-15", ""),
//    STUDENT6(2126, "STDNT", "888030006", "student6", "nmcdonald@kuali.edu", "student6", "nmcdonald", true, "Chemistry", "Nellie", "N", "McDonald", "Female", "Ms.", "", "144 Scenic Drive", "Boston", "MA", "02295", "1952-12-02", ""),
//    STUDENT7(2006, "STDNT", "888030007", "student7", "kthompson@kuali.edu", "student7", "kthompson", true, "Civil", "Kimberly", "K", "Thompson", "Female", "Ms.", "", "163 Ziemlak Circle", "Lee", "MA", "01264", "1948-04-20", ""),
    STUDENT8(2155, "STDNT", "888030008", "student8", "ahopkins@kuali.edu", "student8", "ahopkins", true, "CompSci", "Amber", "D", "Hopkins", "Female", "Ms.", "", "178 Johnny Drive", "Boylston", "MA", "01505", "1941-03-14", ""),
    STUDENT9(2374, "STDNT", "888030009", "student9", "jmanning@kuali.edu", "student9", "jmanning", true, "CrimCrimJus", "Johnny", "F", "Manning", "Male", "Mr.", "", "85 Rosella Street", "Brockton", "MA", "02401", "1943-03-19", ""),
    STUDENT10(2406, "STDNT", "", "student10", "epittman@kuali.edu", "student10", "epittman", true, "Economics", "Eddie", "S", "Pittman", "Male", "Mr.", "", "178 Hazen Lane", "North Easton", "MA", "02356", "1949-11-23", ""),
    STUDENT11(2132, "STDNT", "888030011", "student11", "tburton@kuali.edu", "student11", "tburton", true, "English", "Tracy", "P", "Burton", "Female", "Ms.", "", "154 North Bunn Street", "Winchester", "MA", "01890", "1945-05-25", "");
    private int personId;
    private String affiliation;
    private String ssn;
    private String principalId;
    private String principalName;
    private String entityId;
    private String password;
    private boolean active;
    private String dept;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String title;
    private String suffix;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String birthDate;
    private String deceasedDate;

    private ProcessPocPersonEnum(int personId,
            String affiliation,
            String ssn,
            String principalId,
            String principalName,
            String entityId,
            String password,
            boolean active,
            String dept,
            String firstName,
            String middleName,
            String lastName,
            String gender,
            String title,
            String suffix,
            String street,
            String city,
            String state,
            String zip,
            String birthDate,
            String deceasedDate) {
        this.personId = personId;
        this.affiliation = affiliation;
        this.ssn = ssn;
        this.principalId = principalId;
        this.principalName = principalName;
        this.entityId = entityId;
        this.password = password;
        this.active = active;
        this.dept = dept;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.title = title;
        this.suffix = suffix;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.birthDate = birthDate;
        this.deceasedDate = deceasedDate;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    
    
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
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

    
    
    public String getFormattedName() {
        StringBuilder bldr = new StringBuilder();
        bldr.append(firstName);
        if (middleName != null) {
            bldr.append(" ");
            bldr.append(middleName);
        }
        bldr.append(" ");
        bldr.append(lastName);
        if (this.suffix != null) {
            bldr.append(" ");
            bldr.append(suffix);
        }
        return bldr.toString();
    }
}
