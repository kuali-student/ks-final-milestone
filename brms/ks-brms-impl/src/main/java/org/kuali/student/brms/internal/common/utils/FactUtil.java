/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.brms.internal.common.utils;


import org.kuali.student.brms.factfinder.dto.FactStructureInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FactUtil {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(FactUtil.class);

    /**
     * Creates a criteria key.
     * @param factStructure Fact structure
     * @return Criteria key string
     */
    public static String createCriteriaKey(FactStructureInfo factStructure) {
    	return "FACT" + "." + 
	    	factStructure.getFactStructureId()
	    	+ "." 
	    	+ factStructure.getFactTypeKey();
    }

    /**
     * Creates a fact key.
     * @param factStructure Fact structure
     * @return Fact key string
     */
    public static String createFactKey(FactStructureInfo factStructure) {
    	return "FACT" + "." + 
	    	factStructure.getFactStructureId() 
	    	+ "." 
	    	+ factStructure.getFactTypeKey();
    }
}
