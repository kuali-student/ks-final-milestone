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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A base term resolver that allows it to be used with multiple term names.
 * 
 * The precise term this resolver works for is set at instantiation.
 * 
 * @author Kuali Student Team 
 *
 */
public abstract class AbstractKSTermResolver<T> implements TermResolver<T> {
    private static final Logger log = LoggerFactory
            .getLogger(AbstractKSTermResolver.class);

    protected static final int LOW_COST = 1;

    private String termName;

    private String principalId;
    
    protected ContextInfo getServiceContext() {
        
        ContextInfo info = new ContextInfo();
        
        info.setPrincipalId(principalId);
        info.setAuthenticatedPrincipalId(principalId);
    
        info.setCurrentDate(new Date());
        
        return info;
    }
    /**
     * 
     */
    public AbstractKSTermResolver(String termName, String principalId) {
        this.termName = termName;
        this.principalId = principalId;
    }

    @Override
    public Set<String> getPrerequisites() {
        return new HashSet<String>();
    }

    @Override
    public String getOutput() {
        return termName;
    }

    @Override
    public Set<String> getParameterNames() {
        return new HashSet<String>();
    }

    @Override
    public int getCost() {
        return LOW_COST;
    }

    @Override
    public abstract T resolve(Map<String, Object> resolvedPrereqs,
            Map<String, String> parameters) throws TermResolutionException;
    
}
