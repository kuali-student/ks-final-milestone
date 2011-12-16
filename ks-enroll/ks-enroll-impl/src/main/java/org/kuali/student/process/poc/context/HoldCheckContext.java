package org.kuali.student.process.poc.context;

public class HoldCheckContext extends CheckContext {

    private String issueId;

    private String atpKey;

    public static HoldCheckContext createHoldContext(String issueId, String atpKey) {
        return new HoldCheckContext(issueId, atpKey);
    }

    private HoldCheckContext(String issueId, String atpKey) {
        super();
        this.issueId = issueId;
        this.atpKey = atpKey;
    }

    public String getIssueId() {
        return issueId;
    }

    public String getAtpKey() {
        return atpKey;
    }

}
