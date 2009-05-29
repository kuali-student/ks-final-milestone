package org.kuali.student.lum.lu.ui.course.client.configuration;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.LayoutConfigurationException;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.dictionary.dto.Field;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.dto.State;
import org.kuali.student.core.dictionary.dto.Type;
import org.kuali.student.lum.lu.ui.course.client.configuration.typemanager.CreditCourseLayoutManager;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposal;

public class LULayoutFactory {
    private final ObjectStructure structure; 
    private final Validator validator;
    private final Map<String, Map<String, FieldDescriptor>> indexedFields = new HashMap<String, Map<String,FieldDescriptor>>();

    public LULayoutFactory(ObjectStructure structure, Validator validator) {
        this.structure = structure;
        this.validator = validator;
    }

    public ConfigurableLayout<CluProposal> getLayout(String type, String state) {

        if (type.equalsIgnoreCase(LUConstants.LU_TYPE_CREDIT_COURSE)) {
            return getCreditCourseLayout(type, state);
        }
//      else if (type.equalsIgnoreCase(LUConstants.LU_TYPE_NON_CREDIT_COURSE) {
//      return getNonCreditCourseLayout(type, state);
//      }  etc....
        else {
            throw new LayoutConfigurationException("Invalid type: " + type);            
        }

    }

    /**
     * This method returns a page layout configured for creation or updating of particular state of 
     * a credit course LU
     * 
     * Current valid states listed here :- 
     *    https://test.kuali.org/confluence/display/KULSTU/LuConfig.Constraints.LuTypeLuState
     * 
     * 
     * @param type
     * @param state
     * @return
     */
    private ConfigurableLayout<CluProposal> getCreditCourseLayout(String type, String state) {
        
        final Map<String, FieldDescriptor> fields = getFields(type, state);
        CreditCourseLayoutManager manager = new CreditCourseLayoutManager(fields, validator);
        
        // how do we know which view is req'd, i.e. create, update, view, etc.
        return manager.getCreateUpdateLayout(type, state);
    }

    private Map<String, FieldDescriptor> getFields(String type, String state) {

        Map<String, FieldDescriptor> result = indexedFields.get(type.toLowerCase() + ":" + state.toLowerCase());

        if (result == null) {
            for (Type t : structure.getType()) {
                if (t.getKey().equalsIgnoreCase(type)) {
                    for (State s : t.getState()) {
                        if (s.getKey().equalsIgnoreCase(state)) {
                            result = new HashMap<String, FieldDescriptor>();
                            for (Field f : s.getField()) {
                                result.put(f.getKey(), (FieldDescriptor)f.getFieldDescriptor());
                            }
                            indexedFields.put(type.toLowerCase() + ":" + state.toLowerCase(), result);
                        }
                    }
                    break;
                }
            }
        }

        return result;
    }


}



