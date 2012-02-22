package org.kuali.student.lum.program.client;

import org.kuali.student.common.ui.client.mvc.DataModel;

/**
 * @author Igor
 */
public enum ProgramStatus {
    SUPERSEDED(ProgramMsgConstants.STATUS_SUPERSEDED, null),
    ACTIVE(ProgramMsgConstants.STATUS_ACTIVE, SUPERSEDED),
    APPROVED(ProgramMsgConstants.STATUS_APPROVED, ACTIVE),
    DRAFT(ProgramMsgConstants.STATUS_DRAFT, APPROVED),
    NOTAPPROVED(ProgramMsgConstants.STATUS_NOTAPPROVED, null);

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

    public static ProgramStatus of(DataModel programModel) {
        return of(ProgramUtils.getProgramState(programModel));
    }
}
