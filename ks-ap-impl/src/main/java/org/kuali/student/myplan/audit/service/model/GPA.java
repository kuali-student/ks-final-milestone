package org.kuali.student.myplan.audit.service.model;

public class GPA {
    public String flag = " ";
//    public String caption = "XX GPA CAPTION XX";
    public float required;
    public float earned;

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setRequired(float required) {
        this.required = required;
    }

    public float getRequired() {
        return required;
    }

    public String getRequiredText() {
        return required + "GPA";
    }

    public boolean showRequired() {
        return !"R".equals(flag);
    }

    public void setEarned(float earned) {
        this.earned = earned;
    }

    public float getEarned() {
        return earned;
    }

    public boolean showEarned() {
        return !"E".equals(flag) && earned > 0.0001f;
    }

    public String getEarnedText() {
        return earned + "GPA";
    }


}
