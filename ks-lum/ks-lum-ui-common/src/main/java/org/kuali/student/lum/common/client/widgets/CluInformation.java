package org.kuali.student.lum.common.client.widgets;


import java.io.Serializable;

public class CluInformation implements Serializable {
    private static final long serialVersionUID = 1123124L;
    private String verIndependentId;
    private String code;
    private String title;
    private String credits;

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
}
