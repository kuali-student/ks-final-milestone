package org.kuali.student.core.population.service.impl;

import org.kuali.student.r2.common.exceptions.DoesNotExistException;

/**
 * Represents student specific information of persons that are students.
 * User: mahtabme
 * Date: 12/9/13
 * Time: 2:28 PM
 */
public enum PopulationTestStudentEnum implements hasPersonId {

    STUDENT1("2001", "888020001", "Biology", PopulationTestConstants.PROGRAM_BACHELOR_OF_SCIENCE, PopulationTestConstants.PROGRAM_LEVEL_UNDERGRADUATE, "freshman", "0"),
    STUDENT2("2002", "888020002", "Humanities", PopulationTestConstants.PROGRAM_MASTER_OF_ARTS, PopulationTestConstants.PROGRAM_LEVEL_GRADUATE, "2", "5"),
    STUDENT3("2003", "888020003", "Computer Science", PopulationTestConstants.PROGRAM_MASTER_OF_SCIENCE, PopulationTestConstants.PROGRAM_LEVEL_GRADUATE, "2", "5"),
    STUDENT4("2004", "888020004", "Computer Science", PopulationTestConstants.PROGRAM_MASTER_OF_SCIENCE, PopulationTestConstants.PROGRAM_LEVEL_GRADUATE, "1", "0"),
    STUDENT5("2005", "888020005", "Computer Science", PopulationTestConstants.PROGRAM_MASTER_OF_SCIENCE, PopulationTestConstants.PROGRAM_LEVEL_GRADUATE, "1", "0"),
    STUDENT6("2006", "888020006", "Arts", PopulationTestConstants.PROGRAM_MASTER_OF_ARTS, PopulationTestConstants.PROGRAM_LEVEL_GRADUATE, "3", "10"),
    STUDENT7("2007", "888020007", "Physics", PopulationTestConstants.PROGRAM_MASTER_OF_SCIENCE, PopulationTestConstants.PROGRAM_LEVEL_GRADUATE, "1", "0"),
    STUDENT8("2008", "888020008", "English", PopulationTestConstants.PROGRAM_BACHELOR_OF_ARTS, PopulationTestConstants.PROGRAM_LEVEL_UNDERGRADUATE, "3", "10"),
    STUDENT9("2009", "888020009", "English", PopulationTestConstants.PROGRAM_MASTER_OF_ARTS, PopulationTestConstants.PROGRAM_LEVEL_GRADUATE, "2", "5"),
    STUDENT10("2010", "888020010", "Biology", PopulationTestConstants.PROGRAM_BACHELOR_OF_ARTS, PopulationTestConstants.PROGRAM_LEVEL_UNDERGRADUATE, "1", "0"),
    STUDENT11("2011", "888020011", "Chemistry", PopulationTestConstants.PROGRAM_BACHELOR_OF_ARTS, PopulationTestConstants.PROGRAM_LEVEL_UNDERGRADUATE, "2", "5"),
    STUDENT12("2012", "888020012", "Chemistry", PopulationTestConstants.PROGRAM_BACHELOR_OF_ARTS, PopulationTestConstants.PROGRAM_LEVEL_UNDERGRADUATE, "3", "10"),
    STUDENT13("2013", "888020013", "Chemistry", PopulationTestConstants.PROGRAM_BACHELOR_OF_ARTS, PopulationTestConstants.PROGRAM_LEVEL_UNDERGRADUATE, "freshman", "0"),
    STUDENT14("2014", "888020014", "Chemistry", PopulationTestConstants.PROGRAM_BACHELOR_OF_ARTS, PopulationTestConstants.PROGRAM_LEVEL_UNDERGRADUATE, "freshman", "0"),
    STUDENT15("2015", "888020015", "Chemistry", PopulationTestConstants.PROGRAM_BACHELOR_OF_ARTS, PopulationTestConstants.PROGRAM_LEVEL_UNDERGRADUATE, "sophomore", "0"),
    STUDENT16("2016", "888020016", "Chemistry", PopulationTestConstants.PROGRAM_BACHELOR_OF_ARTS, PopulationTestConstants.PROGRAM_LEVEL_UNDERGRADUATE, "sophomore", "0"),
    STUDENT17("2017", "888020017", "Chemistry", PopulationTestConstants.PROGRAM_BACHELOR_OF_ARTS, PopulationTestConstants.PROGRAM_LEVEL_UNDERGRADUATE, "junior", "0"),
    STUDENT18("2018", "888020018", "Chemistry", PopulationTestConstants.PROGRAM_BACHELOR_OF_ARTS, PopulationTestConstants.PROGRAM_LEVEL_UNDERGRADUATE, "junior", "0"),
    STUDENT19("2019", "888020019", "Chemistry", PopulationTestConstants.PROGRAM_BACHELOR_OF_ARTS, PopulationTestConstants.PROGRAM_LEVEL_UNDERGRADUATE, "senior", "0"),
    STUDENT20("2020", "888020020", "Chemistry", PopulationTestConstants.PROGRAM_BACHELOR_OF_ARTS, PopulationTestConstants.PROGRAM_LEVEL_UNDERGRADUATE, "senior", "0");


    //////////////////////
    // PROPERTIES
    //////////////////////

    private String personId;
    private String governmentIssuedIdentificationNumber;
    private String dept;
    private String programName;
    private String programLevel;
    private String yearOfStudy;
    private String creditsCompleted;

    ////////////////////////
    // CONSTRUCTORS
    ////////////////////////

    private PopulationTestStudentEnum (String personId,
                                      String governmentIssuedIdentificationNumber,
                                      String dept,
                                      String programName,
                                      String programLevel,
                                      String yearOfStudy,
                                      String creditsCompleted) {
        this.personId = personId;
        this.governmentIssuedIdentificationNumber = governmentIssuedIdentificationNumber;
        this.dept = dept;
        this.programName = programName;
        this.programLevel = programLevel;
        this.yearOfStudy = yearOfStudy;
        this.creditsCompleted = creditsCompleted;
    }

    //////////////////////////
    // GETTERS AND SETTERS
    //////////////////////////

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getGovernmentIssuedIdentificationNumber() {
        return governmentIssuedIdentificationNumber;
    }

    public void setGovernmentIssuedIdentificationNumber(String governmentIssuedIdentificationNumber) {
        this.governmentIssuedIdentificationNumber = governmentIssuedIdentificationNumber;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getProgramLevel() {
        return programLevel;
    }

    public void setProgramLevel(String programLevel) {
        this.programLevel = programLevel;
    }

    public String getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(String yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public String getCreditsCompleted() {
        return creditsCompleted;
    }

    public void setCreditsCompleted(String creditsCompleted) {
        this.creditsCompleted = creditsCompleted;
    }

    /////////////////////////////
    // FUNCTIONALS
    /////////////////////////////

    public static PopulationTestStudentEnum getStudent (String personId) throws DoesNotExistException {
        for (PopulationTestStudentEnum student : PopulationTestStudentEnum.values()) {
            if (student.getPersonId()!=null && student.getPersonId().equals (personId)) {
                return student;
            }
        }
        throw new DoesNotExistException("Student with person id " + personId + " not found");
    }

    @Override
    public String toString() {
        return "PopulationTestStudentEnum{" +
                "personId='" + personId + '\'' +
                ", governmentIssuedIdentificationNumber='" + governmentIssuedIdentificationNumber + '\'' +
                ", dept='" + dept + '\'' +
                ", programName='" + programName + '\'' +
                ", programLevel='" + programLevel + '\'' +
                ", yearOfStudy='" + yearOfStudy + '\'' +
                ", creditsCompleted='" + creditsCompleted + '\'' +
                '}';
    }
}
