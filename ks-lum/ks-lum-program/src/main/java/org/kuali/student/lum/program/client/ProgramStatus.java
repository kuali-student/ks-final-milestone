package org.kuali.student.lum.program.client;

import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public enum ProgramStatus {
    SUPERSEDED(ProgramProperties.get().status_superseded(),null),
    ACTIVE(ProgramProperties.get().status_active(), SUPERSEDED),
    APPROVED(ProgramProperties.get().status_approved(), ACTIVE),
    DRAFT(ProgramProperties.get().status_draft(), APPROVED);

    private final String value;

    private final ProgramStatus nextStatus;

    ProgramStatus(String value, ProgramStatus nextStatus) {
        this.value = value;
        this.nextStatus = nextStatus;
    }

    public String getValue() {
        return value;
    }

    public ProgramStatus getNextStatus() {
        return nextStatus;
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
