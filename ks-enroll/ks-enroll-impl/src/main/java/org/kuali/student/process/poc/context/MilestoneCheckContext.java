package org.kuali.student.process.poc.context;

public class MilestoneCheckContext extends CheckContext {

    private String atpKey;

    private String milestoneTypeKey;

    private String milestoneCheckType;

    public static MilestoneCheckContext createMilestoneCheckContext(String atpKey, String milestoneTypeKey, String milestoneCheckType) {
        return new MilestoneCheckContext(atpKey, milestoneTypeKey, milestoneCheckType);
    }

    private MilestoneCheckContext(String atpKey, String milestoneTypeKey, String milestoneCheckType) {

        this.atpKey = atpKey;
        this.milestoneTypeKey = milestoneTypeKey;
        this.milestoneCheckType = milestoneCheckType;
    }

    public String getAtpKey() {
        return atpKey;
    }

    public String getMilestoneTypeKey() {
        return milestoneTypeKey;
    }

    public String getMilestoneCheckType() {
        return milestoneCheckType;
    }

}
