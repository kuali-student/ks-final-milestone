package org.kuali.student.enrollment.class2.acal.dto;

import java.util.Date;

/**
 * This class represents the results of a calendar search (term, acal or holiday cal results)
 */
public class AcalSearchResult {

    private String id;
    private String name;
    private Date startDate;
    private Date endDate;
    private String stateKey;
    private String stateKeyDisplay;
    private String acalSearchTypeKey;

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStateKey() {
        return stateKey;
    }

    public void setStateKey(String stateKey) {
        this.stateKey = stateKey;
    }

    public String getStateKeyDisplay() {
        return stateKeyDisplay;
    }

    public void setStateKeyDisplay(String stateKeyDisplay) {
        this.stateKeyDisplay = stateKeyDisplay;
    }


    public String getAcalSearchTypeKey() {
        return acalSearchTypeKey;
    }

    public void setAcalSearchTypeKey(String acalSearchTypeKey) {
        this.acalSearchTypeKey = acalSearchTypeKey;
    }

}
