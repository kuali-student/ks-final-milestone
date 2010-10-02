package org.kuali.student.lum.lu.ui.tools.client.configuration;

import java.io.Serializable;

public class CluInformation implements Serializable {
    private static final long serialVersionUID = 1123124L;
    private String id;
    private String code;
    private String title;
    private String credits;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
}
