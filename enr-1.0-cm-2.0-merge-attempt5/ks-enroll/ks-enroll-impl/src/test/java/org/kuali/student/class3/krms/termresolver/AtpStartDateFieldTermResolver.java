/*
 * Copyright 2012 The Kuali Foundation
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
package org.kuali.student.class3.krms.termresolver;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.student.krms.KRMSConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kuali Student Team 
 *
 */
public class AtpStartDateFieldTermResolver extends AbstractKSTermResolver<Date>  {
    private static final Logger log = LoggerFactory
            .getLogger(AtpStartDateFieldTermResolver.class);
    
    /**
     * @param termName
     */
    public AtpStartDateFieldTermResolver(String termName, String principalId) {
        super(termName, principalId);
    }
    
    


    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereq = super.getPrerequisites();
        
        prereq.add(KRMSConstants.CURRENT_TERM_ID);
        
        return prereq;
    }



    @Override
    public Date resolve(Map<String, Object> resolvedPrereqs,
            Map<String, String> parameters) throws TermResolutionException {
        
        AtpInfo atp = (AtpInfo) resolvedPrereqs.get(KRMSConstants.CURRENT_TERM_ID);
        
        if (atp == null)
           throw new TermResolutionException("atp undefined for term = " + KRMSConstants.CURRENT_TERM_ID, this, parameters);
        
        return atp.getStartDate();
            
        
    }

   
    
}
