package org.kuali.student.lum.program.client;

import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public enum ProgramStatus {
	DRAFT(ProgramProperties.get().status_draft()),
	APPROVED(ProgramProperties.get().status_approved()),
	ACTIVE(ProgramProperties.get().status_active());

    private final String value;

    ProgramStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ProgramStatus of(String value) {
        for (ProgramStatus status : values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null;
    }
}
