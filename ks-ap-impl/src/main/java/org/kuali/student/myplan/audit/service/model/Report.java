package org.kuali.student.myplan.audit.service.model;

import java.util.ArrayList;
import java.util.List;

public class Report {

    String graduationDate = "Not Found";

    private String webTitle;

    String degreeProgram = "XX Bachelor of Arts in Communication XX";
    String datePrepared = "XX Feb. 21, 2012 10:18 AM XX";
    String entryDateProgram = "AUT/2010";


    public ArrayList<Section> sectionList = new ArrayList<Section>();
    private ArrayList<String> advisoryList = new ArrayList<String>();

    private boolean complete = false;

    public void setGraduationDate(String graduationDate) {
        this.graduationDate = graduationDate;
    }

    public String getGraduationDate() {
        return graduationDate;
    }


    public String getDegreeProgram() {
        return degreeProgram;
    }

    public void setDegreeProgram(String degreeProgram) {
        this.degreeProgram = degreeProgram;
    }

    public String getDatePrepared() {
        return datePrepared;
    }

    public void setDatePrepared(String datePrepared) {
        this.datePrepared = datePrepared;
    }

    public void setEntryDateProgram(String entryDateProgram) {
        this.entryDateProgram = entryDateProgram;
    }

    public String getEntryDateProgram() {
        return entryDateProgram;
    }

    public Section newSection() {
        Section section = new Section();
        sectionList.add(section);
        return section;
    }

    public ArrayList<Section> getSectionList() {
        return sectionList;
    }


    public Requirement newRequirement() {
        Requirement requirement = new Requirement();
        Section section = null;
        if (sectionList.isEmpty()) {
            section = newSection();
            section.setCaption("(Oops)");
        }

        section = sectionList.get(sectionList.size() - 1);
        section.addRequirement(requirement);
        return requirement;
    }


    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }


    public void addAdvisory(String advisory) {
        advisoryList.add(advisory);
    }

    public List<String> getAdvisoryList() {
        return advisoryList;
    }


    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean getComplete() {
        return complete;
    }

}
