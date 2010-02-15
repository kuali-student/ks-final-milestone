/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.lu.ui.course.client.configuration.mvc;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.proposal.dto.ProposalInfo;
//import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;
import org.kuali.student.lum.lu.dto.CluPublishingInfo;

@Deprecated
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
    public static final String CLU_ADMIN_ORG_INFO_KEY = "adminOrgInfo";
    public static final String CLU_ADMIN_ORG_INFO_CLASS = AdminOrgInfo.class.getName();
    public static final String CLU_TIME_AMOUNT_INFO_KEY = "timeAmountInfo";
    public static final String CLU_TIME_AMOUNT_INFO_CLASS = TimeAmountInfo.class.getName();
    public static final String PROPOSAL_INFO_KEY = "proposalInfo";
    public static final String PROPOSAL_INFO_CLASS = ProposalInfo.class.getName();
    public static final String LO_INFO_KEY = LOInfoModelDTO.DTO_KEY;
    public static final String LO_INFO_CLASS = "org.kuali.student.lum.lo.dto.LoInfo";
	private static final Map<String, String> objectKeyToClassMap = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;
	{
		put(CLU_INFO_KEY, CLU_INFO_CLASS);
		put(CLU_IDENTIFIER_INFO_KEY, CLU_IDENTIFIER_INFO_CLASS);
		put(CLU_INSTRUCTOR_INFO_KEY, CLU_INSTRUCTOR_INFO_CLASS);
		put(CLU_PUBLISHING_INFO_KEY, CLU_PUBLISHING_INFO_CLASS);
        put(CLU_ADMIN_ORG_INFO_KEY, CLU_ADMIN_ORG_INFO_CLASS);
        put(CLU_TIME_AMOUNT_INFO_KEY, CLU_TIME_AMOUNT_INFO_CLASS);
        put(PROPOSAL_INFO_KEY, PROPOSAL_INFO_CLASS);
        put(LO_INFO_KEY, LO_INFO_CLASS);
	}};
	
	private static final Map<String, String> classToObjectKeyMap = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;
	{
		put(CLU_INFO_CLASS, CLU_INFO_KEY);
		put(CLU_IDENTIFIER_INFO_CLASS, CLU_IDENTIFIER_INFO_KEY);
		put(CLU_INSTRUCTOR_INFO_CLASS, CLU_INSTRUCTOR_INFO_KEY);
		put(CLU_PUBLISHING_INFO_CLASS, CLU_PUBLISHING_INFO_KEY);
	    put(CLU_ADMIN_ORG_INFO_CLASS, CLU_ADMIN_ORG_INFO_KEY);
	    put(CLU_TIME_AMOUNT_INFO_CLASS, CLU_TIME_AMOUNT_INFO_KEY);
        put(PROPOSAL_INFO_CLASS, PROPOSAL_INFO_KEY);
        put(LO_INFO_CLASS, LO_INFO_KEY);
	}};
	
	public static Map<String, String> getObjectKeytoClassMap(){
        return objectKeyToClassMap;
	}
	
	public static Map<String, String> getClasstoObjectKeyMap(){
        return classToObjectKeyMap;
	}
}
