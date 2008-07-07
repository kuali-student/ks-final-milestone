package org.kuali.student.rules.common.agenda.entity;

public class AnchorType
{
	private String name;
	
	private String type;

    public AnchorType(String name, String type) {
        super();
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
