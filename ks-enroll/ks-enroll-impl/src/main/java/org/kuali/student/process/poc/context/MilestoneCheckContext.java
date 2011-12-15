package org.kuali.student.process.poc.context;

public class MilestoneCheckContext extends CheckContext {

    private String atpKey;

    private String milestoneTypeKey;

    public static MilestoneCheckContext createMilestoneCheckContext(String atpKey, String milestoneTypeKey) {
        return new MilestoneCheckContext(atpKey, milestoneTypeKey);
    }

    private MilestoneCheckContext(String atpKey, String milestoneTypeKey) {
      
        this.atpKey = atpKey;
        this.milestoneTypeKey = milestoneTypeKey;
    }

    public String getAtpKey() {
        return atpKey;
    }

    public void setAtpKey(String atpKey) {
        this.atpKey = atpKey;
    }

    public String getMilestoneTypeKey() {
        return milestoneTypeKey;
    }

    public void setMilestoneTypeKey(String milestoneTypeKey) {
        this.milestoneTypeKey = milestoneTypeKey;
    }

}
