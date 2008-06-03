package org.kuali.student.brms.agenda.entity;

public class Anchor
{
	private String id;
	
    private String name;
	
	private AnchorType anchorType;

    public Anchor(String id, String name, AnchorType anchorType) {
        this.id = id;
        this.name = name;
        this.anchorType = anchorType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AnchorType getAnchorType() {
        return anchorType;
    }
}
