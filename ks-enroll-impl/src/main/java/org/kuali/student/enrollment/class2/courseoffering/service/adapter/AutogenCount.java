/*
 * Copyright 2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Holds the counts of activity offerings, activity offering clusters, total and invalid registration groups.
 * 
 * @author Kuali Student Team 
 *
 */
public class AutogenCount {
    private static final Logger log = LoggerFactory
            .getLogger(AutogenCount.class);

    private Integer numberOfActivityOfferings;
    private Integer numberOfActivityOfferingClusters;
    private Integer numberOfRegistrationGroups;
    private Integer numberOfInvalidRegistrationGroups;
    
    /**
     * 
     */
    public AutogenCount() {
    }


    


    /**
     * @param numberOfActivityOfferings the numberOfActivityOfferings to set
     */
    public void setNumberOfActivityOfferings(Integer numberOfActivityOfferings) {
        this.numberOfActivityOfferings = numberOfActivityOfferings;
    }





    /**
     * @param numberOfActivityOfferingClusters the numberOfActivityOfferingClusters to set
     */
    public void setNumberOfActivityOfferingClusters(
            Integer numberOfActivityOfferingClusters) {
        this.numberOfActivityOfferingClusters = numberOfActivityOfferingClusters;
    }





    /**
     * @param numberOfRegistrationGroups the numberOfRegistrationGroups to set
     */
    public void setNumberOfRegistrationGroups(Integer numberOfRegistrationGroups) {
        this.numberOfRegistrationGroups = numberOfRegistrationGroups;
    }





    /**
     * @param numberOfInvalidRegistrationGroups the numberOfInvalidRegistrationGroups to set
     */
    public void setNumberOfInvalidRegistrationGroups(
            Integer numberOfInvalidRegistrationGroups) {
        this.numberOfInvalidRegistrationGroups = numberOfInvalidRegistrationGroups;
    }





    /**
     * @return the numberOfActivityOfferings
     */
    public Integer getNumberOfActivityOfferings() {
        return numberOfActivityOfferings;
    }


    /**
     * @return the numberOfActivityOfferingClusters
     */
    public Integer getNumberOfActivityOfferingClusters() {
        return numberOfActivityOfferingClusters;
    }


    /**
     * @return the numberOfRegistrationGroups
     */
    public Integer getNumberOfRegistrationGroups() {
        return numberOfRegistrationGroups;
    }


    /**
     * @return the numberOfInvalidRegistrationGroups
     */
    public Integer getNumberOfInvalidRegistrationGroups() {
        return numberOfInvalidRegistrationGroups;
    }
    
    
    
}
