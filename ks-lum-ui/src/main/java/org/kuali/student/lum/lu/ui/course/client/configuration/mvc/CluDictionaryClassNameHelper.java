package org.kuali.student.lum.lu.ui.course.client.configuration.mvc;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;
import org.kuali.student.lum.lu.dto.CluPublishingInfo;

public class CluDictionaryClassNameHelper {
	
	//TODO do not add get name from class, hard code them in here to reduce imports req
	public static final String CLU_INFO_KEY = "cluInfo";
	public static final String CLU_INFO_CLASS = CluInfo.class.getName();
	public static final String CLU_IDENTIFIER_INFO_KEY = "cluIdentifierInfo";
	public static final String CLU_IDENTIFIER_INFO_CLASS = CluIdentifierInfo.class.getName();
	public static final String CLU_INSTRUCTOR_INFO_KEY = "cluInstructorInfo";
	public static final String CLU_INSTRUCTOR_INFO_CLASS = CluInstructorInfo.class.getName();
	public static final String CLU_PUBLISHING_INFO_KEY = "cluPublishingInfo";
	public static final String CLU_PUBLISHING_INFO_CLASS = CluPublishingInfo.class.getName();
    public static final String CLU_ADMIN_ORG_INFO_KEY = "cluAdminOrgInfo";
    public static final String CLU_ADMIN_ORG_INFO_CLASS = AdminOrgInfo.class.getName();
	//TODO add the rest
	
	public static Map<String, String> getObjectKeytoClassMap(){
		Map<String, String> theMap = new HashMap<String, String>();
		theMap.put(CLU_INFO_KEY, CLU_INFO_CLASS);
		theMap.put(CLU_IDENTIFIER_INFO_KEY, CLU_IDENTIFIER_INFO_CLASS);
		theMap.put(CLU_INSTRUCTOR_INFO_KEY, CLU_INSTRUCTOR_INFO_CLASS);
		theMap.put(CLU_PUBLISHING_INFO_KEY, CLU_PUBLISHING_INFO_CLASS);
        theMap.put(CLU_ADMIN_ORG_INFO_KEY, CLU_ADMIN_ORG_INFO_CLASS);
		return theMap;
	}
}
