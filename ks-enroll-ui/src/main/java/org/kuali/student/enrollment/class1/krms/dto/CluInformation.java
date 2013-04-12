package org.kuali.student.enrollment.class1.krms.dto;


import java.io.Serializable;

public class CluInformation implements Serializable, Comparable<CluInformation> {

    private static final long serialVersionUID = 1123124L;

    private String cluId;
    private String verIndependentId;
    private String code;
    private String title;
    private String credits;
    private String type;
    private String parentCluId;

    public String getCluId() {
        return cluId;
    }

    public void setCluId(String cluId) {
        this.cluId = cluId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public void setVerIndependentId(String verIndependentId) {
        this.verIndependentId = verIndependentId;
    }

    public String getVerIndependentId() {
        return verIndependentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentCluId() {
        return parentCluId;
    }

    public void setParentCluId(String parentCluId) {
        this.parentCluId = parentCluId;
    }

    public int compareTo(CluInformation clu) {
        return this.code.compareTo(clu.getCode());
    }

    @Override
    public String toString() {
        return this.getVerIndependentId();
    }
}
