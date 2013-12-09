package org.kuali.student.poc.rules.population;

/**
 * Represents student specific information of persons that are students.
 * User: mahtabme
 * Date: 12/9/13
 * Time: 2:28 PM
 */
public enum PopulationPocStudentEnum {

    STUDENT1(2001, "888020001", "Biology", PopulationPocConstants.PROGRAM_BACHELOR_OF_SCIENCE, PopulationPocConstants.PROGRAM_LEVEL_UNDERGRADUATE, "1", "0"),
    STUDENT2(2002, "888020002", "Humanities", PopulationPocConstants.PROGRAM_BACHELOR_OF_ARTS, PopulationPocConstants.PROGRAM_LEVEL_UNDERGRADUATE, "3", "10"),
    STUDENT3(2003, "888020003", "Computer Science", PopulationPocConstants.PROGRAM_MASTER_OF_SCIENCE, PopulationPocConstants.PROGRAM_LEVEL_GRADUATE, "2", "5");

    //////////////////////
    // PROPERTIES
    //////////////////////

    private int personId;
    private String governmentIssuedIdentificationNumber;
    private String dept;
    private String programName;
    private String programLevel;
    private String yearOfStudy;
    private String creditsCompleted;

    ////////////////////////
    // CONSTRUCTORS
    ////////////////////////

    private PopulationPocStudentEnum (int personId,
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
}
