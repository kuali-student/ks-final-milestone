package org.kuali.student.lum.program.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Igor
 */
public class ProgramRegistry {

    private static Data data;

    private static int row;

    private static Enum<?> section;

    private static HashMap<GwtEvent.Type, EventHandler> specializationHandlers = new HashMap<GwtEvent.Type, EventHandler>();

    private static boolean createNew = false;

    public static Data getData() {
        return data;
    }

    public static void setData(Data data) {
        ProgramRegistry.data = data;
    }

    public static int getRow() {
        return row;
    }

    public static void setRow(int row) {
        ProgramRegistry.row = row;
    }

    public static Enum<?> getSection() {
        return section;
    }

    public static void setSection(Enum<?> section) {
        ProgramRegistry.section = section;
    }

    public static void addHandler(GwtEvent.Type<?> type, EventHandler handler) {
        specializationHandlers.put(type, handler);
    }

    public static HashMap<GwtEvent.Type, EventHandler> getSpecializationHandlers() {
        return specializationHandlers;
    }

    public static boolean isCreateNew() {
        return createNew;
    }

    public static void setCreateNew(boolean createNew) {
        ProgramRegistry.createNew = createNew;
    }
    
    /**
     * @return  The validation warnings for the current variation found in the validation warnings stored in
     * the ApplicationContext.
     *  
     */
    public static List<ValidationResultInfo> getVariationWarnings(){
    	String variationPath = ProgramConstants.VARIATIONS+"/"+org.kuali.student.lum.program.client.ProgramRegistry.getRow()+"/";
    	List<ValidationResultInfo> validationWarnings = Application.getApplicationContext().getValidationWarnings();
    	List<ValidationResultInfo> variationWarnings = new ArrayList<ValidationResultInfo>();
    	for (ValidationResultInfo vr:validationWarnings){
    		if (vr.getElement().contains(variationPath)){
    			ValidationResultInfo newVr = new ValidationResultInfo(vr.getElement().substring(variationPath.length()));
    			newVr.setWarning(vr.getMessage());
    			variationWarnings.add(newVr);
    		}
    	}
    	
    	return variationWarnings;
    }
}
