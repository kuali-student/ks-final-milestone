package org.kuali.student.rules.internal.common.util;

import java.util.List;
import java.util.Set;

public interface FactRequest {

    /**
     * @return the learningResults
     */
    public List<Number> getLearningResults();

    /**
     * @return the luiIds
     */
    public Set<String> getLuiIds();

    /**
     * Makes a calculation in the request.
     * 
     * @param courseIDs
     * @return
     */
    public List<Double> compute(List<String> courseIDs);
    
    public String getId();
}