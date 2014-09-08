package org.kuali.student.cm.uif.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reads required message indicator text from a Spring configuration.
 */
public class RequiredIndicatorServiceImpl implements RequiredIndicatorService {

    private static final Logger LOG = LoggerFactory.getLogger(RequiredIndicatorServiceImpl.class);

    private Map<String, Object> indicatorTextMap = new HashMap<>();

    public Map<String, Object> getIndicatorTextMap() {
        return indicatorTextMap;
    }

    public void setIndicatorTextMap(Map indicatorTextMap) {
        this.indicatorTextMap = indicatorTextMap;
    }

    /**
     * Reads the requiredness indicator text out of the configuration map.
     * @param workflowDocumentType The type of the workflow document.
     * @param workflowState The current state of the workflow document.
     * @param propertyName The of the field in question. (For Labels it is the propertyName of the InputField)
     * @return
     */
    @Override
    public String getRequiredIndicatorMessage(String workflowDocumentType, String workflowState, String propertyName) {

        String requiredIndicatorText = null;

        //  Look for a workflow document type specific config. If none exists then grab the 'default' entry.
        Map<String, Object> workflowDocTypeMap = (Map<String, Object>) getIndicatorTextMap().get(workflowDocumentType);
        if (workflowDocTypeMap == null) {
            workflowDocTypeMap = (Map<String, Object>) getIndicatorTextMap().get("default");
            if (workflowDocTypeMap == null) {
                LOG.error("No 'default' configuration defined for RequiredIndicatorServiceImpl.");
                requiredIndicatorText = "";
            }
        }

        if (requiredIndicatorText == null) {
            //  Find the config for the current state
            Map<String, Object> workflowStateMap = (Map<String, Object>) workflowDocTypeMap.get(workflowState);
            if (workflowStateMap == null) {
                LOG.info("No configuration for workflow state [{}] is defined for RequiredIndicatorServiceImpl.", workflowState);
                requiredIndicatorText = "";
            } else {
                //  Find the config for the given propertyName
                List<String> propertyNames = (List<String>) workflowStateMap.get("propertyNames");
                if (propertyNames.contains(propertyName)) {
                    requiredIndicatorText = (String) workflowStateMap.get("text");
                }
            }
        }

        return requiredIndicatorText;
    }
}
