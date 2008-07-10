/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.internal.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This is a description of what this class does - Kamal don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 *
 */
public class CourseEnrollmentRequest implements Request {

    private String id;
    
    private Set<String> luiIds;

    private List<Number> learningResults;
   
    private static Map<String, Double> learningResultMap = new HashMap<String, Double>();
    
    static {
        
        learningResultMap.put("CPR 101", 3.0);
        learningResultMap.put("CPR 201", 3.0);
        learningResultMap.put("CPR 301", 6.0);
        learningResultMap.put("EMS 1001", 18.0);
        learningResultMap.put("FA 001", 1.0);
        learningResultMap.put("WS 001", 4.0);
        learningResultMap.put("LPN 1001", 18.0);
        learningResultMap.put("CPR 105", 6.0);
        learningResultMap.put("CPR 4005", 21.0);
    }
    
    public CourseEnrollmentRequest(String id) {
        this.id = id;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.rules.internal.common.util.Request#getLearningResults()
     */
    public List<Number> getLearningResults() {
        return learningResults;
    }

    /**
     * @param learningResults the learningResults to set
     */
    public void setLearningResults(List<Number> learningResults) {
        this.learningResults = learningResults;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.rules.internal.common.util.Request#getLuiIds()
     */
    public Set<String> getLuiIds() {
        return luiIds;
    }

    /**
     * @param luiIds the luiIds to set
     */
    public void setLuiIds(Set<String> luiIds) {
        this.luiIds = luiIds;
    }
    
    
    /**
     * 
     * This method finds the intersection of courses taken and courses for which credit has to be computed
     * and then creates a list of credits
     * 
     * @param courseIDs
     * @return
     */
    public List<Double> compute(List<String> courseIDs) {
        List<Double> learningResultList = new ArrayList<Double>();
        
        Set<String> courseIDSet = new HashSet<String>(courseIDs);
        
        courseIDSet.retainAll(luiIds);         
        
        for (String courseID : courseIDSet) {
            learningResultList.add(learningResultMap.get(courseID.trim()));
        }
        
        return learningResultList;
    }

    public String getId() {
        return id;
    }
}
