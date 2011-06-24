package org.kuali.student.lum.program.client.major;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public enum ActionType {
    NO_ACTION(ProgramProperties.get().programAction_title()),
    MODIFY(ProgramProperties.get().programAction_modify()),
    RETIRE(ProgramProperties.get().programAction_retire()),
    MODIFY_VERSION(ProgramProperties.get().programAction_modifyVersion()),
    PROPOSED_PROGRAM_MODIFICATION(ProgramProperties.get().programAction_proposedProgramModification());

    private final String value;

    private static List<String> values;

    ActionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static List<String> getValues() {
        if (values == null) {
            values = new ArrayList<String>();
            for (ActionType actionType : ActionType.values()) {
                values.add(actionType.getValue());
            }
        }
        return values;
    }

    public static List<String> getValues(boolean showAllActions){
    	// There is a states page which shows valid states.  Make needs to update it.
        // It is at: https://wiki.kuali.org/display/KULSTG/Course%2C+Proposal%2C+and+Program+Action+Dropdown+Items
        // NOTE: Use same states as "Modify with Version" on page above (it is equivalent to modify with proposal)
        
        
        // We need to show the PROPOSED_PROGRAM_MODIFICATION option when state is  
        if (showAllActions){
    		return getValues();
    	} else {
    		List<String> values = new ArrayList<String>();
    		values.add(NO_ACTION.getValue());
    		values.add(MODIFY.getValue());
    		values.add(RETIRE.getValue());
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
