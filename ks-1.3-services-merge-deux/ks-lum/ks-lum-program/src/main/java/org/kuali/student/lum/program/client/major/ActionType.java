package org.kuali.student.lum.program.client.major;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.lum.program.client.ProgramStatus;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public enum ActionType {
    NO_ACTION(ProgramProperties.get().programAction_title()),
    MODIFY(ProgramProperties.get().programAction_modify()),
    MODIFY_VERSION(ProgramProperties.get().programAction_modifyVersion());

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
    	if (showAllActions){
    		return getValues();
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
