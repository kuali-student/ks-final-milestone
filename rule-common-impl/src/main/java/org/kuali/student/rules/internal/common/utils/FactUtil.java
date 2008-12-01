package org.kuali.student.rules.internal.common.utils;


import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
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
    public static String createCriteriaKey(FactStructureDTO factStructure) {
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
    public static String createFactKey(FactStructureDTO factStructure) {
    	return "FACT" + "." + 
	    	factStructure.getFactStructureId() 
	    	+ "." 
	    	+ factStructure.getFactTypeKey();
    }
}
