package org.kuali.student.enrollment.class1.krms.dto;


import java.io.Serializable;

public class CluInformation extends CluCore implements Serializable {

    private static final long serialVersionUID = 1123124L;

    private String cluId;
    private String verIndependentId;
    private String title;
    private String description;
    private String type;
    private String state;
    private String parentCluId;

    public CluInformation() {
        super();
    }

    public CluInformation(String code, String shortName, String credits) {
        super(code, shortName, credits);
    }

    public String getCluId() {
        return cluId;
    }

    public void setCluId(String cluId) {
        this.cluId = cluId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getParentCluId() {
        return parentCluId;
    }

    public void setParentCluId(String parentCluId) {
        this.parentCluId = parentCluId;
    }

    public void clear() {
        super.clear();
        this.cluId = null;
        this.verIndependentId = null;
        this.title = null;
        this.description = null;
        this.type = null;
        this.state = null;
        this.parentCluId = null;
    }

    @Override
    public String toString() {
        return this.getVerIndependentId();
    }
}
