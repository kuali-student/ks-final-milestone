package org.kuali.student.r2.common.util;

import org.kuali.student.core.atp.service.AtpService;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.util.Arrays;

public class KSKeyGenerator  {

	/**
	 * 
	 * @param serviceName the name of the service that the create method is invoked on, for all class II services having the same base class I service the key will have same prefix
	 * @param dtoName the DTO for which the name is being created, for e,g Terms will be named like FALL, SPRING, SUMMER  etc
	 * @param atpUniqueIdentifier  convenient readable identifier, terms  or academic calendar could have year and month etc, LUIs could have coursename+year 

	 * @return
	 */
	public static String generateKey(final String serviceName, final String dtoName, final String atpUniqueIdentifier )throws InvalidParameterException {
		
		String key= null; 
		
		String [] namesArrayAtpType = new String[]{AcademicCalendarService.class.getName(), AtpService.class.getName()};
		String[] namesArrayLuiType = new String[] {LuiService.class.getName()};
		String[] namesArrayLPRType = new String[] {LprService.class.getName()};
		
		if(Arrays.asList(namesArrayAtpType).contains(serviceName)){
			
			key = AtpServiceConstants.ATP_KEY_PREFIX;
			
		}else if(Arrays.asList( namesArrayLuiType ).contains(serviceName)){
			key = LuiServiceConstants.LUI_KEY_PREFIX;
		}else if(Arrays.asList( namesArrayLPRType ).contains(serviceName)){
			key = LprServiceConstants.LPR_KEY_PREFIX;
		}
		
		if(key!=null){
			key=key+"."+dtoName+"."+atpUniqueIdentifier;
		}else{
			throw new InvalidParameterException("Check if the service is defined in the names array");
		}
		
		
		return key;
	}
}
