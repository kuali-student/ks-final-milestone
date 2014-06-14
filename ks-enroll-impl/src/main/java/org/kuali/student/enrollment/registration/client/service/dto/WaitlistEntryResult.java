package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by swedev on 5/21/2014.
 *
 * This object is used to give ordering information about waitlist entries.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WaitlistEntryResult", propOrder = {
        "personId", "order", "primaryActivityType", "primaryLuiId", "primaryLprId", "aoWaitlistOrder"})
public class WaitlistEntryResult {

    String personId;

    int order = 0;  // current position for the primary activity (in this case the registration group)

    // each system can be configured differently. In our implementation the Registration Group is the primary WL activity
    String primaryActivityType;

    String primaryLuiId;    // in this case the luiId of the registration group
    String primaryLprId;    // in this case the lprId of the registration group that ties the user to the lui

    List<RegistrationCountResult> aoWaitlistOrder; // gives the position for the non-primary ao's (lec, lab, disc, etc)

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getPrimaryActivityType() {
        return primaryActivityType;
    }

    public void setPrimaryActivityType(String primaryActivityType) {
        this.primaryActivityType = primaryActivityType;
    }

    public String getPrimaryLuiId() {
        return primaryLuiId;
    }

    public void setPrimaryLuiId(String primaryLuiId) {
        this.primaryLuiId = primaryLuiId;
    }

    public String getPrimaryLprId() {
        return primaryLprId;
    }

    public void setPrimaryLprId(String primaryLprId) {
        this.primaryLprId = primaryLprId;
    }

    public List<RegistrationCountResult> getAoWaitlistOrder() {
        if(aoWaitlistOrder == null) aoWaitlistOrder = new ArrayList<RegistrationCountResult>();

        return aoWaitlistOrder;
    }

    public void setAoWaitlistOrder(List<RegistrationCountResult> aoWaitlistOrder) {
        this.aoWaitlistOrder = aoWaitlistOrder;
    }
}
