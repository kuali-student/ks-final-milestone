package org.kuali.student.myplan.audit.service.model;

public class Count {
    public String flag = " ";
    public int required = 100;
//    public int earned = 1;
    public int needs = 99;

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setRequired(int required) {
        this.required = required;
    }
    public int getRequired() { return required; }
    public String getRequiredText()
    {
        return required + " " + ( required == 1 ? "course" : "courses" );
    }

    public boolean showRequired() {
        return !"R".equals(flag);
    }

    public boolean showEarned() {
        return !"E".equals(flag);
    }

    public void setNeeds(int needs) {
        this.needs = needs;
    }
    public int getNeeds() { return needs; }

    public String getNeedsText() {
        return needs + " " + (needs == 1 ? "course" : "courses");
    }


    public boolean showNeeds() {
        return needs > 0.0001f;
    }


}
