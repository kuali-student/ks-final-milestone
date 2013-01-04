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

import java.util.Map;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kuali Student Team 
 *
 */
public class AtpTermResolver extends AbstractKSTermResolver<AtpInfo> {
    private static final Logger log = LoggerFactory
            .getLogger(AtpTermResolver.class);
    private AtpService atpService;
    
    /*
     * My first attempt passed this in as a parameter but it didn't work with auto term resolution.
     * only the nearest term can be parameterized and still work with auto resolution.
     */
    private String atpId;

    /**
     * @param termName
     * @param principalId
     */
    public AtpTermResolver(String termName, String principalId, String atpId, AtpService atpService) {
        super(termName, principalId);
        this.atpId = atpId;
        this.atpService = atpService;
    }

    
   


    
   






    @Override
    public AtpInfo resolve(Map<String, Object> resolvedPrereqs,
            Map<String, String> parameters) throws TermResolutionException {
        
        try {
            AtpInfo term = atpService.getAtp(atpId, getServiceContext());
            
            return term;
         } catch (Exception e) {
             throw new TermResolutionException("failed to load Atp with id = " + atpId, this, parameters, e);
         } 
    }
}
