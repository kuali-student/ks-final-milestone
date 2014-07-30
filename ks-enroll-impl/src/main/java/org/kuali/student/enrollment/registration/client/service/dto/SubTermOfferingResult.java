package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubTermOfferingResult", propOrder = {
        "startDate", "endDate", "name", "subTermId"})

public class SubTermOfferingResult {
    private Date startDate;
    private Date endDate;
    private String name;
    private String subTermId;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubTermId() {
        return subTermId;
    }

    public void setSubTermId(String subTermId) {
        this.subTermId = subTermId;
    }
}
