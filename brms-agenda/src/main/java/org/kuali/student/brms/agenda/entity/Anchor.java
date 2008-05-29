package org.kuali.student.brms.agenda.entity;

public class Anchor
{
	private String name;
	
	private AnchorType anchorType;

    public Anchor(String name, AnchorType anchorType) {
        super();
        this.name = name;
        this.anchorType = anchorType;
    }

    public String getName() {
        return name;
    }

    public AnchorType getAnchorType() {
        return anchorType;
    }
}
