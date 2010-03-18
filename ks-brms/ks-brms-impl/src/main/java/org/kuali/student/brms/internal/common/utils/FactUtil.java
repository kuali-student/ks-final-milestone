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
