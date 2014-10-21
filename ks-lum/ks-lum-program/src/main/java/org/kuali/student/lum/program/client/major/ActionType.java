package org.kuali.student.lum.program.client.major;

import java.util.ArrayList;
import java.util.List;


import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.lum.program.client.ProgramMsgConstants;

/**
 * @author Igor
 */
public enum ActionType {
    NO_ACTION(ProgramMsgConstants.PROGRAMACTION_TITLE),
    MODIFY(ProgramMsgConstants.PROGRAMACTION_MODIFY),
    RETIRE(ProgramMsgConstants.PROGRAMACTION_RETIRE),
    MODIFY_VERSION(ProgramMsgConstants.PROGRAMACTION_MODIFYVERSION),
    PROPOSED_PROGRAM_MODIFICATION(ProgramMsgConstants.PROGRAMACTION_PROPOSEDPROGRAMMODIFICATION);

    private final String value;

    ActionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return Application.getApplicationContext().getUILabel(ProgramMsgConstants.PROGRAM_MSG_GROUP, value);
    }
    
    /**
     * This method will send back a list of values to populate the action box
     * drop-down with.  
     * <p>
     * The list may differ between core, credential, and major discipline type programs.
     * <p>
     * The list may differ if you are working on the latest (most recent) version or a
     * historical version.
     * 
     * @param latestVersion we are working with the most recent version of the program
     * @return
     */
    public static List<String> getValuesForMajorDiscipline(boolean islatestVersion){
        // There is a states page which shows valid states.  Mike needs to update it.
        // It is at: https://wiki.kuali.org/display/KULSTG/Course%2C+Proposal%2C+and+Program+Action+Dropdown+Items
        // NOTE: Use same states as "Modify with Version" on page above (it is equivalent to modify with proposal)
 
        if (islatestVersion){
            List<String> values = new ArrayList<String>();
            values.add(NO_ACTION.getValue());
            values.add(MODIFY.getValue());
            values.add(RETIRE.getValue());
            return values;
        } else {
            List<String> values = new ArrayList<String>();
            values.add(NO_ACTION.getValue());
            values.add(MODIFY.getValue());
            values.add(RETIRE.getValue());
            return values;
        }
    }
    /**
     * This method will send back a list of values to populate the action box
     * drop-down with.  
     * <p>
     * The list may differ between core, credential, and major discipline type programs.
     * <p>
     * The list may differ if you are working on the latest (most recent) version or a
     * historical version.
     * 
     * @param latestVersion we are working with the most recent version of the program
     * @return
     */
    public static List<String> getValuesForCredentialProgram(boolean islatestVersion){
        // There is a states page which shows valid states.  Mike needs to update it.
        // It is at: https://wiki.kuali.org/display/KULSTG/Course%2C+Proposal%2C+and+Program+Action+Dropdown+Items
        // NOTE: Use same states as "Modify with Version" on page above (it is equivalent to modify with proposal)
 
        if (islatestVersion){
            List<String> values = new ArrayList<String>();
            values.add(NO_ACTION.getValue());
            values.add(MODIFY.getValue());
            values.add(MODIFY_VERSION.getValue());
            return values;
        } else {
            List<String> values = new ArrayList<String>();
            values.add(NO_ACTION.getValue());
            values.add(MODIFY.getValue());
            return values;
        }
    }
    
    /**
     * This method will send back a list of values to populate the action box
     * drop-down with.  
     * <p>
     * The list may differ between core, credential, and major discipline type programs.
     * <p>
     * The list may differ if you are working on the latest (most recent) version or a
     * historical version.
     * 
     * @param latestVersion we are working with the most recent version of the program
     * @return
     */
    public static List<String> getValuesForCoreProgram(boolean islatestVersion){
        // There is a states page which shows valid states.  Mike needs to update it.
        // It is at: https://wiki.kuali.org/display/KULSTG/Course%2C+Proposal%2C+and+Program+Action+Dropdown+Items
        // NOTE: Use same states as "Modify with Version" on page above (it is equivalent to modify with proposal)
 
        if (islatestVersion){
            List<String> values = new ArrayList<String>();
            values.add(NO_ACTION.getValue());
            values.add(MODIFY.getValue());
            values.add(MODIFY_VERSION.getValue());
            return values;
        } else {
            List<String> values = new ArrayList<String>();
            values.add(NO_ACTION.getValue());
            values.add(MODIFY.getValue());
            return values;
        }
    }
    
 
    
    public static ActionType of(String value) {
        for (ActionType actionType : values()) {
            if (actionType.getValue().equals(value)) {
                return actionType;
            }
        }
        return null;
    }
}
